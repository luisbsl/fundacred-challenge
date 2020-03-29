package br.com.fundacred.challenge.auth.service.exception;

import org.springframework.http.HttpStatus;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;

/**
 * 
 * @author luisbsl
 *
 */
public class BadRequestException extends RestRequestException {

	private static final long serialVersionUID = 6790598650701912563L;

	public BadRequestException(RestBodyResponse restBodyResponse) {
		super(restBodyResponse, HttpStatus.BAD_REQUEST);
	}

}
