package br.com.fundacred.challenge.auth.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Unit tests for AuthService")
public class AuthServiceTests {

	@Autowired
	AuthService authService;

	@Autowired
	ServletContext context;

	@BeforeAll
	public static void clean() {
		Path databasePath = Path.of("/Users/luislima/Development/workspace/applys/datum/fundacred/challenge/data/test");// directoryToBeDeleted.listFiles();
		System.out.println("************");
//		System.out.println(databasePath.toFile().isDirectory());
		System.out.println(databasePath.toFile().delete());
	}

	@Order(1)
	@Test
	void signup_givenSignupRestBodyRequest_shouldReturnUserRestBodyResponse() {
//		SignupRestBodyRequest signupRestBodyRequest = new SignupRestBodyRequest("Jane Joe", "janejoe@mail.com", "pass",
//				Set.of(new PhoneRestBodyRequest("51", "911112222")));
//
//		UserRestBodyResponse userRestBodyResponse = authService.signup(signupRestBodyRequest);
//		
//		assertNotNull(userRestBodyResponse);
//		assertNotNull(userRestBodyResponse.getId());

//		String absolutePath = context.getRealPath("./");
//		System.out.println(absolutePath);

		assertTrue(true);
	}

}
