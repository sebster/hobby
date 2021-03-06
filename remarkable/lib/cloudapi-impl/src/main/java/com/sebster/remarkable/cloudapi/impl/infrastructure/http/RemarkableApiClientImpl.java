package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import static com.sebster.commons.collections.Lists.map;
import static com.sebster.commons.strings.Strings.nullSafeTrim;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sebster.commons.io.InputStreamProcessor;
import com.sebster.remarkable.cloudapi.RemarkableDownloadLink;
import com.sebster.remarkable.cloudapi.RemarkableException;
import com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableApiClient;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemarkableApiClientImpl implements RemarkableApiClient {

	private static final String REGISTRATION_URL =
			"https://my.remarkable.com/token/json/2/device/new";

	private static final String DEREGISTRATION_URL =
			"https://my.remarkable.com/token/json/2/device/delete";

	private static final String TOKEN_URL =
			"https://my.remarkable.com/token/json/2/user/new";

	private static final String STORAGE_DISCOVERY_URL =
			"https://service-manager-production-dot-remarkable-production.appspot.com/service/json/1/document-storage" +
					"?environment=production&group=auth0|5a68dc51cb30df3877a1d7c4&apiVer=2";

	private static final ParameterizedTypeReference<List<ItemInfoJsonDto>> ITEM_LIST_TYPE = new ParameterizedTypeReference<>() {
	};

	private final @NonNull RestTemplate restTemplate;

	private final ConcurrentHashMap<String, String> sessionTokens = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, String> storageHosts = new ConcurrentHashMap<>();

	public RemarkableApiClientImpl(@NonNull RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String register(@NonNull UUID clientId, @NonNull String clientType, @NonNull String code) {
		log.debug("register: clientId={} clientType={} code={}", clientId, clientType, code);
		return getBody(handleClientError(() -> restTemplate.exchange(
				REGISTRATION_URL,
				POST,
				request(new RegistrationRequestJsonDto(code, clientType, clientId.toString()), null),
				String.class
		), e -> new RemarkableException("Could not register: " + nullSafeTrim(e.getResponseBodyAsString()))));
	}

	@Override
	public void unregister(@NonNull String loginToken) {
		log.debug("unregister");
		handleClientError(() -> restTemplate.exchange(
				DEREGISTRATION_URL,
				POST,
				emptyRequest(loginToken),
				Void.class
		), e -> new RemarkableException("Could not unregister: " + nullSafeTrim(e.getResponseBodyAsString())));
	}

	@Override
	public String login(@NonNull String authToken) {
		return sessionTokens.computeIfAbsent(authToken, token -> {
			log.debug("login");
			return getBody(handleClientError(() -> restTemplate.exchange(
					TOKEN_URL,
					POST,
					emptyRequest(token),
					String.class
			), e -> new RemarkableException("Could not authenticate: " + nullSafeTrim(e.getResponseBodyAsString()))));
		});
	}

	@Override
	public List<ItemInfoDto> list(@NonNull String sessionToken, boolean includeBlobUrl) {
		log.debug("list all: includeBlobUrl={}", includeBlobUrl);
		List<ItemInfoJsonDto> list = getBody(handleClientError(() -> restTemplate.exchange(
				getStorageUrlBuilder(sessionToken, "docs")
						.queryParam("withBlob", includeBlobUrl)
						.build().toUri(),
				GET,
				emptyRequest(sessionToken),
				ITEM_LIST_TYPE
		), e -> new RemarkableException("Could not list items: " + nullSafeTrim(e.getResponseBodyAsString()))));
		log.debug("list all: {} items", list.size());
		return map(list, ItemInfoJsonDto::unmarshal);
	}

	@Override
	public void download(@NonNull RemarkableDownloadLink link, @NonNull InputStreamProcessor processor) {
		log.debug("download: link={}", link);
		restTemplate.execute(link.getUrl(), GET, null, response -> {
			processor.process(response.getBody());
			return null;
		});
	}

	@Override
	public List<ItemInfoDto> updateMetadata(@NonNull String sessionToken, @NonNull Collection<ItemInfoDto> itemInfos) {
		log.debug("updateMetadata: items={}", itemInfos);
		List<ItemInfoJsonDto> results = getBody(handleClientError(() -> restTemplate.exchange(
				getStorageUrlBuilder(sessionToken, "upload/update-status").build().toUri(),
				PUT,
				request(map(itemInfos, ItemInfoJsonDto::marshal), sessionToken),
				ITEM_LIST_TYPE
		), e -> new RemarkableException("Could not update items: " + nullSafeTrim(e.getResponseBodyAsString()))));
		return map(results, ItemInfoJsonDto::unmarshal);
	}

	@Override
	public List<ItemInfoDto> delete(@NonNull String sessionToken, @NonNull Collection<ItemInfoDto> itemInfos) {
		log.debug("delete: items={}", itemInfos);
		List<ItemInfoJsonDto> results = getBody(handleClientError(() -> restTemplate.exchange(
				getStorageUrlBuilder(sessionToken, "delete").build().toUri(),
				PUT,
				request(map(itemInfos, ItemInfoJsonDto::marshalIdAndVersionOnly), sessionToken),
				ITEM_LIST_TYPE
		), e -> new RemarkableException("Could not delete items: " + nullSafeTrim(e.getResponseBodyAsString()))));
		return map(results, ItemInfoJsonDto::unmarshal);
	}

	private String getStorageHost(String sessionToken) {
		return storageHosts.computeIfAbsent(sessionToken, token -> {
			log.debug("getStorageHost");
			StorageHostResponseJsonDto response = getBody(handleClientError(() -> restTemplate.exchange(
					STORAGE_DISCOVERY_URL,
					GET,
					emptyRequest(sessionToken),
					StorageHostResponseJsonDto.class
			), e -> new RemarkableException("Could not get storage host: " + nullSafeTrim(e.getResponseBodyAsString()))));
			if (!"OK".equalsIgnoreCase(response.getStatus())) {
				throw new RemarkableException("Storage host not available: status=" + response.getStatus());
			}
			log.debug("getStorageHost: {}", response.getHost());
			return response.getHost();
		});
	}

	private UriComponentsBuilder getStorageUrlBuilder(String sessionToken, String serviceUrl) {
		return UriComponentsBuilder
				.fromUriString("https:///document-storage/json/2/")
				.host(getStorageHost(sessionToken))
				.path(serviceUrl);
	}

	private HttpEntity<Void> emptyRequest(String authToken) {
		return request(null, authToken);
	}

	private <T> HttpEntity<T> request(T body, String authToken) {
		HttpHeaders headers = new HttpHeaders();
		if (authToken != null) {
			headers.add(AUTHORIZATION, "Bearer " + authToken);
		}
		return new HttpEntity<>(body, headers);
	}

	private <T> T getBody(ResponseEntity<T> responseEntity) {
		T body = responseEntity.getBody();
		if (body == null) {
			// Should not happen.
			throw new IllegalStateException("No response body");
		}
		return body;
	}

	private <T> ResponseEntity<T> handleClientError(
			Supplier<ResponseEntity<T>> requestExecutor,
			Function<HttpClientErrorException, RemarkableException> exceptionFactory
	) {
		try {
			return requestExecutor.get();
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().is4xxClientError()) {
				throw exceptionFactory.apply(e);
			}
			throw e;
		}
	}

}
