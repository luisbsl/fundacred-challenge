package br.com.fundacred.challenge.auth.service.exception;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;

public class BadRequestException extends RestRequestException {

	private static final long serialVersionUID = 6790598650701912563L;

	public BadRequestException(RestBodyResponse restBodyResponse) {
		super(restBodyResponse);
	}

}
