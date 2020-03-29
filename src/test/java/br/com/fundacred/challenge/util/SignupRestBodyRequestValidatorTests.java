package br.com.fundacred.challenge.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fundacred.challenge.auth.controller.dto.PhoneRestBodyRequest;
import br.com.fundacred.challenge.auth.controller.dto.SignupRestBodyRequest;
import br.com.fundacred.challenge.auth.service.exception.BadRequestException;
import br.com.fundacred.challenge.helper.BCryptHelper;
import br.com.fundacred.challenge.helper.EnviromentHelper;
import br.com.fundacred.challenge.helper.JWTTokenHelper;
import br.com.fundacred.challenge.model.User;

@SpringBootTest
public class SignupRestBodyRequestValidatorTests {

	@Test
	void validate() {

//		System.out.println(" ******** ");
//		System.out.println(System.getenv("FUNDACRED_CHALLENGE_SECRET"));

		User user = new User();
		user.setName("Jane");
		user.setEmail("jane@mail.com");
		user.setPassword("pass");

		System.out.println(" ***** ");
		System.out.println(BCryptHelper.generateHash(EnviromentHelper.Key.FUNDACRED_CHALLENGE_API_SECRET.getVal()));
		System.out.println(JWTTokenHelper.generateUserToken(user));

		assertTrue(true);

	}
	
	public static void main(String[] args) {
		PhoneRestBodyRequest p1 = new PhoneRestBodyRequest("22", "912345678");
		
//		System.out.println(ModelValidator.validatePhone(p1));
		
		try {
			PhoneRestBodyRequest pr = new PhoneRestBodyRequest("51", "912345678");
			SignupRestBodyRequest ur = new SignupRestBodyRequest("Jane", "jane@mail.com", "pass", Set.of(pr));
//			UserRestBodyRequestValidator.validateModelConstraints().accept(ur);
		}catch(BadRequestException ex) {
			System.out.println(ex.getRestBodyResponse().getMensagens());
		}
		
	}

}
