package br.com.fundacred.challenge.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fundacred.challenge.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
