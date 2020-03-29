package br.com.fundacred.challenge.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fundacred.challenge.model.User;

/**
 * 
 * @author luisbsl
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
	
	Optional<User> findByEmail(final String email);

}
