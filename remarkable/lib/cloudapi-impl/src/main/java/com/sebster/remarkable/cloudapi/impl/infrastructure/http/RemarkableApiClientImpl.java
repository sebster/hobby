package com.sebster.remarkable.cloudapi.impl.infrastructure.http;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sebster.remarkable.cloudapi.impl.controller.ItemInfoDto;
import com.sebster.remarkable.cloudapi.impl.controller.RemarkableApiClient;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemarkableApiClientImpl implements RemarkableApiClient {

	private static final String REGISTRATION_URL =
			"https://my.remarkable.com/token/json/2/device/new";

	private static final String TOKEN_URL =
			"https://my.remarkable.com/token/json/2/user/new";

	private static final String STORAGE_DISCOVERY_URL =
			"https://service-manager-production-dot-remarkable-production.appspot.com/service/json/1/document-storage" +
					"?environment=production&group=auth0|5a68dc51cb30df3877a1d7c4&apiVer=2";

	private static final String DOCUMENT_URL_TEMPLATE =
			"https://storage-host//document-storage/json/2/docs";

	private static final ParameterizedTypeReference<List<ItemInfoDto>> ITEM_LIST_TYPE = new ParameterizedTypeReference<>() {
	};

	private final @NonNull RestTemplate restTemplate;

	private final ConcurrentHashMap<String, String> sessionTokens = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, String> storageHosts = new ConcurrentHashMap<>();

	public RemarkableApiClientImpl(@NonNull RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String login(@NonNull String authToken) {
		return sessionTokens.computeIfAbsent(authToken, token -> {
			log.debug("login");
			return getBody(restTemplate.exchange(TOKEN_URL, POST, emptyRequest(token), String.class));
		});
	}

	@Override
	public String register(@NonNull UUID clientId, @NonNull String clientType, @NonNull String code) {
		log.debug("register: clientId={} clientType={} code={}", clientId, clientType, code);
		return getBody(restTemplate.exchange(
				REGISTRATION_URL,
				GET,
				request(new RegistrationRequestDto(code, clientType, clientId.toString()), null),
				String.class
		));
	}

	@Override
	public List<ItemInfoDto> list(@NonNull String sessionToken, boolean includeBlobUrl) {
		log.debug("list all: includeBlobUrl={}", includeBlobUrl);
		List<ItemInfoDto> list = getBody(restTemplate.exchange(
				getDocumentUrlBuilder(sessionToken).queryParam("withBlob", includeBlobUrl).toUriString(),
				GET,
				emptyRequest(sessionToken),
				ITEM_LIST_TYPE
		));
		log.debug("list all: {} items", list.size());
		return list;
	}

	@Override
	public ItemInfoDto list(@NonNull String sessionToken, @NonNull UUID id, boolean includeBlobUrl) {
		log.debug("list: id={} includeBlobUrl={}", id, includeBlobUrl);
		return getItemInfo(getBody(restTemplate.exchange(
				getDocumentUrlBuilder(sessionToken)
						.queryParam("doc", id.toString())
						.queryParam("withBlob", includeBlobUrl)
						.toUriString(),
				GET,
				emptyRequest(sessionToken),
				ITEM_LIST_TYPE
		)));
	}

	private String getStorageHost(String sessionToken) {
		return storageHosts.computeIfAbsent(sessionToken, token -> {
			log.debug("getStorageHost");
			String storageHost = getBody(restTemplate.exchange(
					STORAGE_DISCOVERY_URL,
					GET,
					emptyRequest(sessionToken),
					StorageHostResponseDto.class
			)).getHost();
			log.debug("getStorageHost: {}", storageHost);
			return storageHost;
		});
	}

	public UriComponentsBuilder getDocumentUrlBuilder(String sessionToken) {
		return UriComponentsBuilder.fromUriString(DOCUMENT_URL_TEMPLATE).host(getStorageHost(sessionToken));
	}

	private HttpEntity<Void> emptyRequest(String authToken) {
		return request(null, authToken);
	}

	private <T> HttpEntity<T> request(T body, String authToken) {
		HttpHeaders headers = new HttpHeaders();
		if (authToken != null) {
			headers.add(AUTHORIZATION, "Bearer " + authToken);
		}
		return new HttpEntity<T>(body, headers);
	}

	private <T> T getBody(ResponseEntity<T> responseEntity) {
		T body = responseEntity.getBody();
		if (body == null) {
			// Should not happen.
			throw new IllegalStateException("Missing response body");
		}
		;
		return body;
	}

	private ItemInfoDto getItemInfo(List<ItemInfoDto> items) {
		if (items.size() != 1) {
			// Should not happen.
			throw new IllegalStateException("Incorrect number of items: expected 1, got " + items.size());
		}
		ItemInfoDto itemInfo = items.get(0);
		if (!itemInfo.isSuccess()) {
			throw new UncheckedIOException(new IOException("Item " + itemInfo.getId() + ": " + itemInfo.getMessage()));
		}
		return items.get(0);
	}

}
