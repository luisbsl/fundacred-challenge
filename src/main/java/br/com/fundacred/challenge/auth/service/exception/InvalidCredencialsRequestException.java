package br.com.fundacred.challenge.auth.service.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;

public class InvalidCredencialsRequestException extends RestRequestException {

	private static final long serialVersionUID = -7206149119450361788L;

	public InvalidCredencialsRequestException() {
		this.restBodyResponse = new RestBodyResponse(Set.of("Usuário e/ou senha inválidos"), HttpStatus.UNAUTHORIZED);
	}

}