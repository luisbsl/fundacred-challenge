package br.com.fundacred.challenge.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fundacred.challenge.model.User;
import br.com.fundacred.challenge.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User save(final User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> findByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

}
