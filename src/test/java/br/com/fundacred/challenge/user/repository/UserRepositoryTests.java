package br.com.fundacred.challenge.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fundacred.challenge.model.Phone;
import br.com.fundacred.challenge.model.User;

@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;

	@Test
	void save() {
		final User user = new User();
		user.setName("Jake");
		Phone p1 = new Phone();
		p1.setDdd("51");
		p1.setNumber("91111-2222");
		Phone p2 = new Phone();
		p1.setDdd("51");
		p1.setNumber("93333-4444");
		user.setPhones(Set.of(p1, p2));
		final User savedUser = userRepository.save(user);

		System.out.println(" ******* ");
		System.out.println(savedUser);

		assertNotNull(savedUser);
		assertEquals(savedUser, user);
	}

}
