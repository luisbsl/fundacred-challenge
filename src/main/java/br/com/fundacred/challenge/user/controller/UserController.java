package br.com.fundacred.challenge.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<RestBodyResponse> signin(@PathVariable final String id,
			@RequestHeader("token") String token) {
		try {
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		} catch (RestRequestException e) {
			var restBodyResponse = e.getRestBodyResponse();
			return new ResponseEntity<>(restBodyResponse, e.getHttpStatus());
		}
	}

}
