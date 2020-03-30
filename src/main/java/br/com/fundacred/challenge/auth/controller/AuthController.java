package br.com.fundacred.challenge.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fundacred.challenge.auth.controller.dto.RestBodyResponse;
import br.com.fundacred.challenge.auth.controller.dto.SigninRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;
import br.com.fundacred.challenge.auth.service.AuthService;
import br.com.fundacred.challenge.auth.service.exception.RestRequestException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author luisbsl
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Usuário cadastrado com sucesso"),
			@ApiResponse(code = 409, message = "Usuário e/ou senha inválidos"),
			@ApiResponse(code = 400, message = "Erro de campos orbigatórios")
			})
	public ResponseEntity<RestBodyResponse> signup(@RequestBody final SignupRestBodyRequest signupRestBodyRequest) {
		try {
			return new ResponseEntity<>(authService.signup(signupRestBodyRequest), HttpStatus.CREATED);
		} catch (RestRequestException e) {
			return new ResponseEntity<>(e.getRestBodyResponse(), e.getHttpStatus());
		}
	}

	@PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Usuário logado com sucesso"),
			@ApiResponse(code = 400, message = "Erro de campos orbigatórios"),
			@ApiResponse(code = 404, message = "Usuário e/ou senha inválidos"),
			@ApiResponse(code = 401, message = "Usuário e/ou senha inválidos")
			})
	public ResponseEntity<RestBodyResponse> signin(@RequestBody final SigninRestBodyRequest signinRestBodyRequest) {
		try {
			return new ResponseEntity<>(authService.signin(signinRestBodyRequest), HttpStatus.OK);
		} catch (RestRequestException e) {
			return new ResponseEntity<>(e.getRestBodyResponse(), e.getHttpStatus());
		}
	}

}
