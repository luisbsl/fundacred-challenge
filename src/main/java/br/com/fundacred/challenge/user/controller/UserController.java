package br.com.fundacred.challenge.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;
import br.com.fundacred.challenge.auth.service.exception.RestRequestException;
import br.com.fundacred.challenge.user.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author luisbsl
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Usuário autorizado"),
			@ApiResponse(code = 400, message = "Erro de campos orbigatórios"),
			@ApiResponse(code = 404, message = "Usuário não encontrado"),
			@ApiResponse(code = 401, message = "Não autorizado")
			})
	public ResponseEntity<RestBodyResponse> signin(@PathVariable final String id,
			@RequestHeader("token") String token) {
		try {
			return new ResponseEntity<>(userService.profile(id, token), HttpStatus.OK);
		} catch (RestRequestException e) {
			return new ResponseEntity<>(e.getRestBodyResponse(), e.getHttpStatus());
		}
	}

}
