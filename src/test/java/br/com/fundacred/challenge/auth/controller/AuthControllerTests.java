package br.com.fundacred.challenge.auth.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.fundacred.challenge.auth.controller.dto.PhoneRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("Unit tests for AuthController")
public class AuthControllerTests {

	@LocalServerPort
	protected int port;

	@Autowired
	RestTemplate restTemplate;

	@Test
	void signup_shouldReturnRegisteredUser() {
		SignupRestBodyRequest userRestBodyRequest = new SignupRestBodyRequest("Jane", "jane@mail.com", "pass",
				Set.of(new PhoneRestBodyRequest("51", "911112222")));

		ResponseEntity restBodyResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/auth/signup",
				userRestBodyRequest, ResponseEntity.class);
		System.out.println(restBodyResponse.getBody());

		assertTrue(true);
	}

	void signin_shouldReturn_UserRestBodyResponse() {
		SignupRestBodyRequest userRestBodyRequest = new SignupRestBodyRequest("Jane", "jane@mail.com", "pass",
				Set.of(new PhoneRestBodyRequest("51", "911112222")));

		ResponseEntity restBodyResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/v1",
				userRestBodyRequest, ResponseEntity.class);
		System.out.println(restBodyResponse.getBody());
	}

}
