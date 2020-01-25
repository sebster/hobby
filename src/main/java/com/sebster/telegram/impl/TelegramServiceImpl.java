package com.sebster.telegram.impl;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;
import static com.sebster.telegram.api.TelegramSendMessageOptions.defaultSendMessageOptions;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.telegram.api.TelegramException;
import com.sebster.telegram.api.TelegramSendMessageOptions;
import com.sebster.telegram.api.TelegramService;
import com.sebster.telegram.api.TelegramServiceException;
import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramFile;
import com.sebster.telegram.api.data.TelegramFileLink;
import com.sebster.telegram.api.data.TelegramUpdate;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.data.messages.TelegramMessage;
import com.sebster.telegram.impl.dto.TelegramFileDto;
import com.sebster.telegram.impl.dto.TelegramMessageDto;
import com.sebster.telegram.impl.dto.TelegramUpdateDto;
import com.sebster.telegram.impl.dto.request.TelegramSendMessageRequest;
import com.sebster.telegram.impl.dto.response.BaseTelegramResponse;
import com.sebster.telegram.impl.dto.response.TelegramGetFileResponse;
import com.sebster.telegram.impl.dto.response.TelegramGetMeResponse;
import com.sebster.telegram.impl.dto.response.TelegramGetUpdatesResponse;
import com.sebster.telegram.impl.dto.response.TelegramSendMessageResponse;

public class TelegramServiceImpl implements TelegramService, InitializingBean {

	private static final String TELEGRAM_API_BASE_URL = "https://api.telegram.org/bot";
	private static final String TELEGRAM_API_FILE_BASE_URL = "https://api.telegram.org/file/bot";

	private static final String GET_ME = "getMe";
	private static final String GET_UPDATES = "getUpdates";
	private static final String GET_FILE = "getFile";
	private static final String SEND_MESSAGE = "sendMessage";

	private String token;
	private RestTemplate restTemplate;

	public TelegramServiceImpl() {
	}

	public TelegramServiceImpl(String token) {
		this.token = token;
	}

	@Override
	public TelegramUser getMe() {
		return doGet(GET_ME, TelegramGetMeResponse.class).toTelegramUser();
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset) {
		return getUpdates(offset, Optional.empty(), Optional.empty());
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset, Duration timeout) {
		return getUpdates(offset, Optional.empty(), Optional.of(timeout));
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset, int limit) {
		return getUpdates(offset, Optional.of(limit), Optional.empty());
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset, int limit, Duration timeout) {
		return getUpdates(offset, Optional.of(limit), Optional.of(timeout));
	}

	private List<TelegramUpdate> getUpdates(int offset, Optional<Integer> limit, Optional<Duration> timeout) {
		if (limit.isPresent()) {
			Validate.inclusiveBetween(1, 100, limit.get(), "limit must be between 1 and 100: %s", limit.get());
		}
		Map<String, Object> queryParams = new LinkedHashMap<>();
		queryParams.put("offset", offset);
		if (limit.isPresent()) {
			queryParams.put("limit", limit.get());
		}
		if (timeout.isPresent()) {
			queryParams.put("timeout", timeout.get().getSeconds());
		}
		List<TelegramUpdateDto> updates = doGet(GET_UPDATES, queryParams, TelegramGetUpdatesResponse.class);
		return updates.stream().map(update -> update.toTelegramUpdate()).collect(toList());
	}

	@Override
	public TelegramFileLink getFileLink(String fileId) {
		try {
			TelegramFileDto response = doGet(GET_FILE, singletonMap("file_id", fileId), TelegramGetFileResponse.class);
			return response.toTelegramFileLink(new URL(telegramApiFileBaseUrl()));
		} catch (MalformedURLException e) {
			// Should not happen.
			throw new TelegramException(e);
		}
	}

	@Override
	public TelegramFileLink getFileLink(TelegramFile file) {
		return getFileLink(requireNonNull(file, "file").getFileId());
	}

	@Override
	public TelegramMessage sendMessage(long chatId, String text) {
		return sendMessageImpl(chatId, text, defaultSendMessageOptions());
	}

	@Override
	public TelegramMessage sendMessage(TelegramChat chat, String text) {
		return sendMessage(requireNonNull(chat, "chat").getId(), defaultSendMessageOptions(), text);
	}

	@Override
	public TelegramMessage sendMessage(String channel, String text) {
		return sendMessageImpl(requireNonNull(channel, "channel"), text, defaultSendMessageOptions());
	}

	@Override
	public TelegramMessage sendMessage(long chatId, TelegramSendMessageOptions options, String text) {
		return sendMessageImpl(chatId, text, options);
	}

	@Override
	public TelegramMessage sendMessage(TelegramChat chat, TelegramSendMessageOptions options, String text) {
		return sendMessage(requireNonNull(chat, "chat").getId(), text);
	}

	@Override
	public TelegramMessage sendMessage(String channel, TelegramSendMessageOptions options, String text) {
		return sendMessageImpl(requireNonNull(channel, "channel"), text, options);
	}

	private TelegramMessage sendMessageImpl(Object target, String text, TelegramSendMessageOptions options) {
		TelegramSendMessageRequest request = new TelegramSendMessageRequest(target, requireNonNull(text, "text"),
				options.getParseMode().orElse(null), options.getDisableWebPagePreview().orElse(null),
				options.getDisableNotification().orElse(null));
		TelegramMessageDto response = doPost(SEND_MESSAGE, request, TelegramSendMessageResponse.class);
		return response.toTelegramMessage();
	}

	private <RESULT, RESPONSE extends BaseTelegramResponse<RESULT>> RESULT doGet(String method,
			Class<RESPONSE> responseClass) {
		return doGet(method, emptyMap(), responseClass);
	}

	private <RESULT, RESPONSE extends BaseTelegramResponse<RESULT>> RESULT doGet(String method,
			Map<String, Object> queryParams, Class<RESPONSE> responseClass) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(telegramApiBaseUrl()).path(method);
		queryParams.forEach((name, value) -> uriBuilder.queryParam(name, value));
		RESPONSE response = restTemplate.getForObject(uriBuilder.toUriString(), responseClass);
		return getResult(response);
	}

	private <RESULT, RESPONSE extends BaseTelegramResponse<RESULT>> RESULT doPost(String method, Object request,
			Class<RESPONSE> responseClass) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(telegramApiBaseUrl()).path(method);
		RESPONSE response = restTemplate.postForObject(uriBuilder.toUriString(), request, responseClass);
		return getResult(response);
	}

	private <RESULT, RESPONSE extends BaseTelegramResponse<RESULT>> RESULT getResult(RESPONSE response) {
		if (!response.isOk()) {
			throw new TelegramServiceException(response.getErrorCode(), response.getDescription());
		}
		return response.getResult();
	}

	private String telegramApiBaseUrl() {
		return TELEGRAM_API_BASE_URL + token + "/";
	}

	private String telegramApiFileBaseUrl() {
		return TELEGRAM_API_FILE_BASE_URL + token + "/";
	}

	public void setToken(String token) {
		this.token = requireNonNull(token, "token");
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (restTemplate == null) {
			restTemplate = new RestTemplate(asList(new MappingJackson2HttpMessageConverter(objectMapper())));
		}
	}

	private ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder.json().serializationInclusion(NON_NULL).propertyNamingStrategy(SNAKE_CASE)
				.build();
	}

}
