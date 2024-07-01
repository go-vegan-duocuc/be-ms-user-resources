package cl.govegan.msuserresources.services.userprofileservice;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cl.govegan.msuserresources.model.UserProfile;
import cl.govegan.msuserresources.web.request.UserProfileRequest;

@Service
public interface  UserProfileService {

   public UserProfile getUserProfile(Authentication authentication);

   public UserProfile createUserProfile(UserProfileRequest request, Authentication authentication);

   public UserProfile updateUserProfile(UserProfile userProfile);

   public UserProfile addFavoriteRecipeById(String recipeId);

   public UserProfile getFavoriteRecipes();

   public UserProfile deleteFavoriteRecipeById(String recipeId);

   public Boolean isFavoriteRecipe(String recipeId);

   public UserProfile addFavoriteFoodById (String foodId);

   public UserProfile getFavoriteFoods();

   public UserProfile deleteFavoriteFoodById(String foodId);

   public Boolean isFavoriteFood(String foodId);

   public UserProfile addFoodAlergiesById(String foodId);

   public UserProfile getFoodAlergies();

   public UserProfile deleteFoodAlergiesById(String foodId);

   public Boolean isFoodAlergies(String foodId);

   public UserProfile addUnwantedFoodById(String foodId);

   public UserProfile getUnwantedFoods();

   public UserProfile deleteUnwantedFoodById(String foodId);

   public Boolean isUnwantedFood(String foodId);

}
