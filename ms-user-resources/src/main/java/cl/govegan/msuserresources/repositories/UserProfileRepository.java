package cl.govegan.msuserresources.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.govegan.msuserresources.model.UserProfile;


public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

   Optional<UserProfile> findByUserId (String userId);

}
