package cl.govegan.msuserresources.utils;

import java.util.Map;

import cl.govegan.msuserresources.model.Gender;
import cl.govegan.msuserresources.model.UserProfile;
import cl.govegan.msuserresources.web.request.UserProfileRequest;

public class RequestUtils {

   public static UserProfile toUserProfile (UserProfileRequest request, Map<String, String> userData) {
      return UserProfile.builder()
            .userId(userData.get("userId"))
            .username(userData.get("username"))
            .email(request.getEmail())
            .name(request.getName())
            .profilePicture(request.getProfilePicture())
            .age(request.getAge())
            .gender(Gender.valueOf(request.getGender()))
            .weight(request.getWeight())
            .height(request.getHeight())
            .city(request.getCity())
            .country(request.getCountry())
            .allergies(request.getAllergies())
            .favoriteFoods(request.getFavoriteFoods())
            .unwantedFoods(request.getUnwantedFoods())
            .favoriteRecipes(request.getFavoriteRecipes())
            .caloriesPerDay(request.getCaloriesPerDay())
            .waterPerDay(request.getWaterPerDay())
            .title(request.getTitle())
            .build();

   }
}
