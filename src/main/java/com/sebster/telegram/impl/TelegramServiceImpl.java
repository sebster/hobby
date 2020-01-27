package com.sebster.telegram.impl;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;
import static com.sebster.telegram.api.TelegramSendMessageOptions.defaultOptions;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebster.telegram.api.TelegramSendMessageOptions;
import com.sebster.telegram.api.TelegramService;
import com.sebster.telegram.api.TelegramServiceException;
import com.sebster.telegram.api.TelegramUpdate;
import com.sebster.telegram.api.data.TelegramChat;
import com.sebster.telegram.api.data.TelegramFile;
import com.sebster.telegram.api.data.TelegramFileLink;
import com.sebster.telegram.api.data.TelegramUser;
import com.sebster.telegram.api.messages.TelegramMessage;
import com.sebster.telegram.impl.dto.data.TelegramFileDto;
import com.sebster.telegram.impl.dto.data.TelegramMessageDto;
import com.sebster.telegram.impl.dto.methods.BaseTelegramResponseDto;
import com.sebster.telegram.impl.dto.methods.getfile.TelegramGetFileResponseDto;
import com.sebster.telegram.impl.dto.methods.getme.TelegramGetMeResponseDto;
import com.sebster.telegram.impl.dto.methods.getupdates.TelegramGetUpdatesResponseDto;
import com.sebster.telegram.impl.dto.methods.getupdates.TelegramUpdateDto;
import com.sebster.telegram.impl.dto.methods.sendmessage.TelegramSendMessageRequestDto;
import com.sebster.telegram.impl.dto.methods.sendmessage.TelegramSendMessageResponseDto;
import lombok.NonNull;

public class TelegramServiceImpl implements TelegramService {

	private static final String TELEGRAM_API_BASE_URL = "https://api.telegram.org/bot";
	private static final String TELEGRAM_API_FILE_BASE_URL = "https://api.telegram.org/file/bot";

	private static final String GET_ME = "getMe";
	private static final String GET_UPDATES = "getUpdates";
	private static final String GET_FILE = "getFile";
	private static final String SEND_MESSAGE = "sendMessage";

	private final @NonNull String token;
	private final @NonNull RestTemplate restTemplate;

	public TelegramServiceImpl(String token) {
		this.token = token;
		this.restTemplate = restTemplate();
	}

	@Override
	public TelegramUser getMe() {
		return doGet(GET_ME, TelegramGetMeResponseDto.class).toTelegramUser();
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset) {
		return getUpdatesImpl(offset, null, null);
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset, @NonNull Duration timeout) {
		return getUpdatesImpl(offset, null, timeout);
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset, int limit) {
		return getUpdatesImpl(offset, limit, null);
	}

	@Override
	public List<TelegramUpdate> getUpdates(int offset, int limit, @NonNull Duration timeout) {
		return getUpdatesImpl(offset, limit, timeout);
	}

	private List<TelegramUpdate> getUpdatesImpl(int offset, Integer limit, Duration timeout) {
		if (limit != null) {
			Validate.inclusiveBetween(1, 100, limit, "limit must be between 1 and 100: %s", limit);
		}
		Map<String, Object> queryParams = new LinkedHashMap<>();
		queryParams.put("offset", offset);
		if (limit != null) {
			queryParams.put("limit", limit);
		}
		if (timeout != null) {
			queryParams.put("timeout", timeout.getSeconds());
		}
		List<TelegramUpdateDto> updates = doGet(GET_UPDATES, queryParams, TelegramGetUpdatesResponseDto.class);
		return updates.stream().map(TelegramUpdateDto::toTelegramUpdate).collect(toList());
	}

	@Override
	public TelegramFileLink getFileLink(@NonNull String fileId) {
		try {
			TelegramFileDto response = doGet(GET_FILE, singletonMap("file_id", fileId), TelegramGetFileResponseDto.class);
			return response.toTelegramFileLink(new URL(telegramApiFileBaseUrl()));
		} catch (MalformedURLException e) {
			// Should not happen.
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public TelegramFileLink getFileLink(@NonNull TelegramFile file) {
		return getFileLink(file.getFileId());
	}

	@Override
	public TelegramMessage sendMessage(long chatId, @NonNull String text) {
		return sendMessageImpl(chatId, text, defaultOptions());
	}

	@Override
	public TelegramMessage sendMessage(@NonNull TelegramChat chat, @NonNull String text) {
		return sendMessage(chat.getId(), defaultOptions(), text);
	}

	@Override
	public TelegramMessage sendMessage(@NonNull String channel, @NonNull String text) {
		return sendMessageImpl(channel, text, defaultOptions());
	}

	@Override
	public TelegramMessage sendMessage(long chatId, @NonNull TelegramSendMessageOptions options, @NonNull String text) {
		return sendMessageImpl(chatId, text, options);
	}

	@Override
	public TelegramMessage sendMessage(@NonNull TelegramChat chat, @NonNull TelegramSendMessageOptions options,
			@NonNull String text) {
		return sendMessageImpl(chat.getId(), text, options);
	}

	@Override
	public TelegramMessage sendMessage(@NonNull String channel, @NonNull TelegramSendMessageOptions options, @NonNull String text) {
		return sendMessageImpl(channel, text, options);
	}

	private TelegramMessage sendMessageImpl(Object target, String text, TelegramSendMessageOptions options) {
		TelegramSendMessageRequestDto request = new TelegramSendMessageRequestDto(target, text, options);
		TelegramMessageDto response = doPost(SEND_MESSAGE, request, TelegramSendMessageResponseDto.class);
		return response.toTelegramMessage();
	}

	private <RESULT, RESPONSE extends BaseTelegramResponseDto<RESULT>> RESULT doGet(String method, Class<RESPONSE> responseClass) {
		return doGet(method, emptyMap(), responseClass);
	}

	private <RESULT, RESPONSE extends BaseTelegramResponseDto<RESULT>> RESULT doGet(
			String method, Map<String, Object> queryParams, Class<RESPONSE> responseClass
	) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(telegramApiBaseUrl()).path(method);
		queryParams.forEach(uriBuilder::queryParam);
		RESPONSE response = restTemplate.getForObject(uriBuilder.toUriString(), responseClass);
		return getResult(response);
	}

	private <RESULT, RESPONSE extends BaseTelegramResponseDto<RESULT>> RESULT doPost(
			String method, Object request, Class<RESPONSE> responseClass
	) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(telegramApiBaseUrl()).path(method);
		RESPONSE response = restTemplate.postForObject(uriBuilder.toUriString(), request, responseClass);
		return getResult(response);
	}

	private <RESULT, RESPONSE extends BaseTelegramResponseDto<RESULT>> RESULT getResult(RESPONSE response) {
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

	private static RestTemplate restTemplate() {
		return new RestTemplate(singletonList(new MappingJackson2HttpMessageConverter(objectMapper())));
	}

	private static ObjectMapper objectMapper() {
		return json()
				.serializationInclusion(NON_NULL)
				.propertyNamingStrategy(SNAKE_CASE)
				.build();
	}

}
