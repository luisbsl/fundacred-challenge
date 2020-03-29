package br.com.fundacred.challenge.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fundacred.challenge.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	Optional<User> findByEmail(final String email);

}
