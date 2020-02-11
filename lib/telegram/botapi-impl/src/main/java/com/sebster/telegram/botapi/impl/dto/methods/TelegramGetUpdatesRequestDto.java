package com.sebster.telegram.botapi.impl.dto.methods;

import java.util.Optional;

import lombok.Value;

@Value
public final class TelegramGetUpdatesRequestDto {

	int offset;
	Integer limit;
	Integer timeout;

	/**
	 * Optional. Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of
	 * previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is
	 * considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * Optional. Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
	 */
	public Optional<Integer> getLimit() {
		return Optional.ofNullable(limit);
	}

	/**
	 * Optional. Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
	 */
	public Optional<Integer> getTimeout() {
		return Optional.ofNullable(timeout);
	}

}
