package br.com.fundacred.challenge.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.com.fundacred.challenge.auth.controller.dto.PhoneRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SigninRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.UserRestBodyResponse;
import br.com.fundacred.challenge.auth.service.exception.BadRequestException;
import br.com.fundacred.challenge.auth.service.exception.EmailConflictRequestException;
import br.com.fundacred.challenge.auth.service.exception.EmailNotFoundRequestException;
import br.com.fundacred.challenge.auth.service.exception.InvalidCredencialsRequestException;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Unit tests for AuthService")
public class AuthServiceTests {

	@Autowired
	AuthService authService;

	@Test
	@Order(1)
	void signup_givenSignupRestBodyRequest_shouldReturnUserRestBodyResponse() {
		UserRestBodyResponse userRestBodyResponse = signup();

		assertNotNull(userRestBodyResponse);
		assertNotNull(userRestBodyResponse.getId());
	}

	@Test
	@Order(2)
	void signup_givenSignupRestBodyRequest_shouldThrowBadRequestException() {
		final SignupRestBodyRequest invalidSignupRestBodyRequest = new SignupRestBodyRequest("Jane Joe", "", "pass",
				Set.of(new PhoneRestBodyRequest("51", "911112222")));

		BadRequestException exception = assertThrows(BadRequestException.class,
				() -> authService.signup(invalidSignupRestBodyRequest));

		assertNotNull(exception.getRestBodyResponse().getMensagens().stream().filter(m -> m.contains("email"))
				.findFirst().get());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
	}

	@Test
	@Order(3)
	void signup_givenSignupRestBodyRequest_shouldThrowEmailConflictRequestException() {
		final SignupRestBodyRequest duplicatedSignupRestBodyRequest = new SignupRestBodyRequest("Jane Joe",
				"janejoe@mail.com", "pass", Set.of(new PhoneRestBodyRequest("51", "911112222")));

		EmailConflictRequestException exception = assertThrows(EmailConflictRequestException.class,
				() -> authService.signup(duplicatedSignupRestBodyRequest));

		assertNotNull(exception.getRestBodyResponse().getMensagens().stream()
				.filter(m -> m.equals("Usuário e/ou senha inválidos")).findFirst().get());
		assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
	}

	@Test
	@Order(4)
	void signin_givenSigninRestBodyRequest_shouldReturnUserRestBodyResponse() {
		signup();
		final SigninRestBodyRequest signin = new SigninRestBodyRequest("janejoe@mail.com", "pass");
		final UserRestBodyResponse userRestBodyResponse = authService.signin(signin);

		assertNotNull(userRestBodyResponse);
		assertNotNull(userRestBodyResponse.getId());
		assertNotNull(userRestBodyResponse.getToken());
		assertNotNull(userRestBodyResponse.getLastLogin());
	}

	@Test
	@Order(5)
	void signin_givenSignupRestBodyRequest_shouldThrowBadRequestException() {
		final SigninRestBodyRequest invalidSigninRestBodyRequest = new SigninRestBodyRequest("Jane Joe", "pass");

		BadRequestException exception = assertThrows(BadRequestException.class,
				() -> authService.signin(invalidSigninRestBodyRequest));

		assertNotNull(exception.getRestBodyResponse().getMensagens().stream().filter(m -> m.contains("email"))
				.findFirst().get());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
	}

	@Test
	@Order(6)
	void signin_givenSignupRestBodyRequest_shouldThrowEmailNotFoundRequestException() {
		final SigninRestBodyRequest invalidSigninRestBodyRequest = new SigninRestBodyRequest("jane@gmail.com", "pass");

		EmailNotFoundRequestException exception = assertThrows(EmailNotFoundRequestException.class,
				() -> authService.signin(invalidSigninRestBodyRequest));

		assertNotNull(exception.getRestBodyResponse().getMensagens().stream().filter(m -> m.contains("senha"))
				.findFirst().get());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
	}

	@Test
	@Order(7)
	void signin_givenSignupRestBodyRequest_shouldThrowInvalidCredencialsRequestException() {
		signup();
		final SigninRestBodyRequest invalidSigninRestBodyRequest = new SigninRestBodyRequest("janejoe@mail.com",
				"passss");

		InvalidCredencialsRequestException exception = assertThrows(InvalidCredencialsRequestException.class,
				() -> authService.signin(invalidSigninRestBodyRequest));

		assertNotNull(exception.getRestBodyResponse().getMensagens().stream().filter(m -> m.contains("senha"))
				.findFirst().get());
		assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());
	}

	private UserRestBodyResponse signup() {
		final SignupRestBodyRequest signup = new SignupRestBodyRequest("Jane Joe", "janejoe@mail.com", "pass",
				Set.of(new PhoneRestBodyRequest("51", "911112222")));
		return authService.signup(signup);
	}

}
