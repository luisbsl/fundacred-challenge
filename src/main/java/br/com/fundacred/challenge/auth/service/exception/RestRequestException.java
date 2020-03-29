package br.com.fundacred.challenge.auth.service.exception;

import org.springframework.http.HttpStatus;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;

/**
 * 
 * @author luisbsl
 *
 */
public class RestRequestException extends RuntimeException {

	private static final long serialVersionUID = -4539193502093509329L;
	protected RestBodyResponse restBodyResponse;
	protected HttpStatus httpStatus;

	public RestRequestException() {
	}

	public RestRequestException(RestBodyResponse restBodyResponse) {
		super();
		this.restBodyResponse = restBodyResponse;
	}

	public RestRequestException(RestBodyResponse restBodyResponse, HttpStatus httpStatus) {
		super();
		this.restBodyResponse = restBodyResponse;
		this.httpStatus = httpStatus;
	}

	public RestBodyResponse getRestBodyResponse() {
		return restBodyResponse;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
