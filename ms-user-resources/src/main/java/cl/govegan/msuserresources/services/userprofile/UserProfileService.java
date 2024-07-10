package cl.govegan.msuserresources.services.userprofile;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cl.govegan.msuserresources.model.UserProfile;
import cl.govegan.msuserresources.web.request.UserProfileRequest;

@Service
public interface  UserProfileService {

   public UserProfile getUserProfile(Authentication authentication);

   public UserProfile createUserProfile(Authentication authentication, UserProfileRequest userProfileRequest);

   public UserProfile updateUserProfile(Authentication authentication, UserProfileRequest userProfileRequest);

   public UserProfile addFavoriteRecipeById(Authentication authentication, String recipeId);

   public List<String> getFavoriteRecipes(Authentication authentication);

   public void deleteFavoriteRecipeById(Authentication authentication,String recipeId);

   public Boolean isFavoriteRecipe(Authentication authentication, String recipeId);

   public UserProfile addFavoriteFoodById (Authentication authentication, String foodId);

   public List<String> getFavoriteFoods(Authentication authentication);

   public void deleteFavoriteFoodById(Authentication authentication, String foodId);

   public Boolean isFavoriteFood(Authentication authentication, String foodId);

   public UserProfile addFoodAlergiesById(Authentication authentication, String foodId);

   public List<String> getFoodAlergies(Authentication authentication);

   public void deleteFoodAlergiesById(Authentication authentication, String foodId);

   public Boolean isFoodAlergies(Authentication authentication, String foodId);

   public UserProfile addUnwantedFoodById(Authentication authentication, String foodId);

   public List<String> getUnwantedFoods(Authentication authentication);

   public void deleteUnwantedFoodById(Authentication authentication, String foodId);

   public Boolean isUnwantedFood(Authentication authentication, String foodId);

   public void deleteUserProfile(Authentication authentication);

}
