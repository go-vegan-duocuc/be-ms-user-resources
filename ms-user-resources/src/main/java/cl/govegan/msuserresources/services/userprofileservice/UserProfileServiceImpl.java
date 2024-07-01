package cl.govegan.msuserresources.services.userprofileservice;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cl.govegan.msuserresources.model.Gender;
import cl.govegan.msuserresources.model.UserProfile;
import cl.govegan.msuserresources.repositories.UserProfileRepository;
import cl.govegan.msuserresources.services.jwtservice.JwtService;
import cl.govegan.msuserresources.web.request.UserProfileRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

   private final UserProfileRepository userProfileRepository;
   private final JwtService jwtService;

   @Override
   public UserProfile getUserProfile(Authentication authentication) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      ObjectId userId = new ObjectId(userData.get("userId"));

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get("userId"));

      if (userProfile.isPresent()) {
         return userProfile.get();
      } else {
         userProfileRepository.save(UserProfile.builder()
               .userId(userData.get("userId"))
               .username(userData.get("username"))
               .email("")
               .name("")
               .profilePicture("")
               .age(0)
               .gender(Gender.OTRO)
               .weight(0)
               .height(0)
               .city("")
               .country("")
               .allergies(new ArrayList<>())
               .favoriteFoods(new ArrayList<>())
               .unwantedFoods(new ArrayList<>())
               .favoriteRecipes(new ArrayList<>())
               .caloriesPerDay(0)
               .waterPerDay(0)
               .title("")
               .build());
         Optional<UserProfile> savedProfile = userProfileRepository.findByUserId(userData.get("userId"));
         return savedProfile.orElseThrow(() -> new RuntimeException("Profile not found after save"));

      }
   }

   @Override
   public UserProfile createUserProfile(UserProfileRequest request, Authentication authentication) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'createUserProfile'");
   }

   @Override
   public UserProfile updateUserProfile(UserProfile userProfile) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'updateUserProfile'");
   }

   @Override
   public UserProfile addFavoriteRecipeById(String recipeId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'addFavoriteRecipeById'");
   }

   @Override
   public UserProfile getFavoriteRecipes() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getFavoriteRecipes'");
   }

   @Override
   public UserProfile deleteFavoriteRecipeById(String recipeId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteFavoriteRecipeById'");
   }

   @Override
   public Boolean isFavoriteRecipe(String recipeId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'isFavoriteRecipe'");
   }

   @Override
   public UserProfile addFavoriteFoodById(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'addFavoriteFoodById'");
   }

   @Override
   public UserProfile getFavoriteFoods() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getFavoriteFoods'");
   }

   @Override
   public UserProfile deleteFavoriteFoodById(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteFavoriteFoodById'");
   }

   @Override
   public Boolean isFavoriteFood(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'isFavoriteFood'");
   }

   @Override
   public UserProfile addFoodAlergiesById(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'addFoodAlergiesById'");
   }

   @Override
   public UserProfile getFoodAlergies() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getFoodAlergies'");
   }

   @Override
   public UserProfile deleteFoodAlergiesById(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteFoodAlergiesById'");
   }

   @Override
   public Boolean isFoodAlergies(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'isFoodAlergies'");
   }

   @Override
   public UserProfile addUnwantedFoodById(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'addUnwantedFoodById'");
   }

   @Override
   public UserProfile getUnwantedFoods() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getUnwantedFoods'");
   }

   @Override
   public UserProfile deleteUnwantedFoodById(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteUnwantedFoodById'");
   }

   @Override
   public Boolean isUnwantedFood(String foodId) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'isUnwantedFood'");
   }
}
