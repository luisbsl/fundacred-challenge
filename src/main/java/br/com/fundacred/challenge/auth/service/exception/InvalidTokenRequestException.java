package br.com.fundacred.challenge.auth.service.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;

/**
 * 
 * @author luisbsl
 *
 */
public class InvalidTokenRequestException extends RestRequestException {

	private static final long serialVersionUID = -7206149119450361788L;

	public InvalidTokenRequestException() {
		super(new RestBodyResponse(Set.of("Não autorizado")), HttpStatus.UNAUTHORIZED);
	}

}