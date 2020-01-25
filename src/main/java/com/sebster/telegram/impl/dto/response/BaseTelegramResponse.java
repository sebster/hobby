package com.sebster.telegram.impl.dto.response;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseTelegramResponse<RESULT> {

	@JsonProperty("ok")
	private boolean ok;

	@JsonProperty("result")
	private RESULT result;

	@JsonProperty("error_code")
	private int errorCode;

	@JsonProperty("description")
	private String description;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public RESULT getResult() {
		return result;
	}

	public void setResult(RESULT result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return reflectionToString(this);
	}

}
