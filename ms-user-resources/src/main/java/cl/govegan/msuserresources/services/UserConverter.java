package cl.govegan.msuserresources.services;

import cl.govegan.msuserresources.controller.FakeAuth;
import cl.govegan.msuserresources.models.Gender;
import cl.govegan.msuserresources.models.Profile;
import cl.govegan.msuserresources.models.Role;
import cl.govegan.msuserresources.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserConverter {

   public User toUser(FakeAuth.RegisterRequest registerRequest, PasswordEncoder passwordEncoder) {

      if ("".equals(registerRequest.getGender())) {
            registerRequest.setGender("OTRO");
      }

      return User.builder()
            .username(registerRequest.getUsername())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .email(registerRequest.getEmail())
            .roles(Set.of(Role.USER))
            .profile(Profile.builder()
                  .name(registerRequest.getName())
                  .profilePicture(registerRequest.getProfilePicture())
                  .age(registerRequest.getAge())
                  .gender(Gender.valueOf(registerRequest.getGender().toUpperCase()))
                  .weight(registerRequest.getWeight())
                  .height(registerRequest.getHeight())
                  .city(registerRequest.getCity())
                  .country(registerRequest.getCountry())
                  .allergies(registerRequest.getAllergies())
                  .favoriteFoods(registerRequest.getFavoriteFoods())
                  .unwantedFoods(registerRequest.getUnwantedFoods())
                  .favoriteRecipes(registerRequest.getFavoriteRecipes())
                  .caloriesPerDay(registerRequest.getCaloriesPerDay())
                  .waterPerDay(registerRequest.getWaterPerDay())
                  .title(registerRequest.getTitle())
                  .build())
            .build();
   }

}
