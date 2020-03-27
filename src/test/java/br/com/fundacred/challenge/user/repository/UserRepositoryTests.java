package br.com.fundacred.challenge.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fundacred.challenge.model.User;

@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;

	@Test
	void save() {
		final User user = new User();
		user.setName("Jake");
		final User savedUser = userRepository.save(user);

		System.out.println(" ******* ");
		System.out.println(savedUser);

		assertNotNull(savedUser);
		assertEquals(savedUser, user);
	}

}
