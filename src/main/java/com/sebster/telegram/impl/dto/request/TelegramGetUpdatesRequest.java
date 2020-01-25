package com.sebster.telegram.impl.dto.request;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.util.Optional;

public final class TelegramGetUpdatesRequest {

	private final int offset;
	private final Optional<Integer> limit;
	private final Optional<Integer> timeout;

	public TelegramGetUpdatesRequest(int offset, Optional<Integer> limit, Optional<Integer> timeout) {
		this.offset = offset;
		this.limit = limit;
		this.timeout = timeout;
	}

	/**
	 * Optional. Identifier of the first update to be returned. Must be greater
	 * by one than the highest among the identifiers of previously received
	 * updates. By default, updates starting with the earliest unconfirmed
	 * update are returned. An update is considered confirmed as soon as
	 * getUpdates is called with an offset higher than its update_id.
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * Optional. Limits the number of updates to be retrieved. Values between
	 * 1—100 are accepted. Defaults to 100
	 */
	public Optional<Integer> getLimit() {
		return limit;
	}

	/**
	 * Optional. Limits the number of updates to be retrieved. Values between
	 * 1—100 are accepted. Defaults to 100
	 */
	public Optional<Integer> getTimeout() {
		return timeout;
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
