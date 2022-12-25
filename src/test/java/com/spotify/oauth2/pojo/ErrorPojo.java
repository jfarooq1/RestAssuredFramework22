package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ErrorPojo {

	@JsonProperty("error")
	private InnerErrorPojo error;

	@JsonProperty("error")
	public InnerErrorPojo getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(InnerErrorPojo error) {
		this.error = error;
	}

}