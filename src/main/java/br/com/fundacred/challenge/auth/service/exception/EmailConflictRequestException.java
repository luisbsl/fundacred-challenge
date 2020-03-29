package br.com.fundacred.challenge.auth.service.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;

public class EmailConflictRequestException extends RestRequestException {

	private static final long serialVersionUID = -7206149119450361788L;

	public EmailConflictRequestException() {
		this.restBodyResponse = new RestBodyResponse(Set.of("E-mail já existente"), HttpStatus.CONFLICT);
	}

}