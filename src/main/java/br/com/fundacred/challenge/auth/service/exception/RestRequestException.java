package br.com.fundacred.challenge.auth.service.exception;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;

public class RestRequestException extends RuntimeException {

	private static final long serialVersionUID = -4539193502093509329L;
	protected RestBodyResponse restBodyResponse;

	public RestRequestException() {
	}

	public RestRequestException(RestBodyResponse restBodyResponse) {
		super();
		this.restBodyResponse = restBodyResponse;
	}

	public RestBodyResponse getRestBodyResponse() {
		return restBodyResponse;
	}

}
