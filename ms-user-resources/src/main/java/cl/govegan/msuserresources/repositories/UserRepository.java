package cl.govegan.msuserresources.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.govegan.msuserresources.models.User;

public interface UserRepository extends MongoRepository<User, String> {

   Optional<User> findByUsername(String username);

}
