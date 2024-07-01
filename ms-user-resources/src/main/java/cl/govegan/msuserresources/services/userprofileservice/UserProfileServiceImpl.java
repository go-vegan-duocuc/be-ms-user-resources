package cl.govegan.msuserresources.services.userprofileservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
   private static final String USER_ID = "userId";

   @Override
   public UserProfile getUserProfile(Authentication authentication) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get();
      } else {
         userProfileRepository.save(UserProfile.builder()
               .userId(userData.get(USER_ID))
               .username(userData.get(USER_ID))
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
         Optional<UserProfile> savedProfile = userProfileRepository.findByUserId(userData.get(USER_ID));
         return savedProfile.orElseThrow(() -> new RuntimeException("Profile not found after save"));

      }
   }

   @Override
   public UserProfile createUserProfile(Authentication authentication, UserProfileRequest userProfileRequest) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         throw new RuntimeException("User profile already exists");
      } else {
         return userProfileRepository.save(UserProfile.builder()
               .userId(userData.get(USER_ID))
               .username(userData.get(USER_ID))
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
      }
   }

   @Override
   public UserProfile updateUserProfile(Authentication authentication, UserProfileRequest userProfileRequest) {
      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         UserProfile profile = userProfile.get();
         profile.setEmail(userProfileRequest.getEmail());
         profile.setName(userProfileRequest.getName());
         profile.setProfilePicture(userProfileRequest.getProfilePicture());
         profile.setAge(userProfileRequest.getAge());
         profile.setGender(Gender.valueOf(userProfileRequest.getGender()));
         profile.setWeight(userProfileRequest.getWeight());
         profile.setHeight(userProfileRequest.getHeight());
         profile.setCity(userProfileRequest.getCity());
         profile.setCountry(userProfileRequest.getCountry());
         profile.setAllergies(userProfileRequest.getAllergies());
         profile.setFavoriteFoods(userProfileRequest.getFavoriteFoods());
         profile.setUnwantedFoods(userProfileRequest.getUnwantedFoods());
         profile.setFavoriteRecipes(userProfileRequest.getFavoriteRecipes());
         profile.setCaloriesPerDay(userProfileRequest.getCaloriesPerDay());
         profile.setWaterPerDay(userProfileRequest.getWaterPerDay());
         profile.setTitle(userProfileRequest.getTitle());
         return userProfileRepository.save(profile);
      } else {
         throw new RuntimeException("User profile not found");
      }

   }

   @Override
   public UserProfile addFavoriteRecipeById(Authentication authentication, String recipeId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         UserProfile profile = userProfile.get();
         profile.getFavoriteRecipes().add(recipeId);
         return userProfileRepository.save(profile);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public List<String> getFavoriteRecipes(Authentication authentication) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get().getFavoriteRecipes();
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public void deleteFavoriteRecipeById(Authentication authentication, String recipeId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         UserProfile profile = userProfile.get();
         profile.getFavoriteRecipes().remove(recipeId);
         userProfileRepository.save(profile);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public Boolean isFavoriteRecipe(Authentication authentication, String recipeId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get().getFavoriteRecipes().contains(recipeId);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public UserProfile addFavoriteFoodById(Authentication authentication, String foodId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         if(userProfile.get().getFavoriteFoods().contains(foodId)) {
            throw new RuntimeException("Food already in favorites");
         }else if(userProfile.get().getUnwantedFoods().contains(foodId)) {
            throw new RuntimeException("Food in unwanted list");
         }else if(userProfile.get().getAllergies().contains(foodId)) {
            throw new RuntimeException("Food in allergies list");
         }else {
            UserProfile profile = userProfile.get();
            profile.getFavoriteFoods().add(foodId);
            return userProfileRepository.save(profile);
         }
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public List<String> getFavoriteFoods(Authentication authentication) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get().getFavoriteFoods();
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public void deleteFavoriteFoodById(Authentication authentication, String foodId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         UserProfile profile = userProfile.get();
         profile.getFavoriteFoods().remove(foodId);
         userProfileRepository.save(profile);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public Boolean isFavoriteFood(Authentication authentication, String foodId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get().getFavoriteFoods().contains(foodId);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public UserProfile addFoodAlergiesById(Authentication authentication, String foodId) {
      
      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {

         if(userProfile.get().getFavoriteFoods().contains(foodId)) {
            throw new RuntimeException("Food in favorites list");
         }else if(userProfile.get().getUnwantedFoods().contains(foodId)) {
            throw new RuntimeException("Food in unwanted list");
         }else if(userProfile.get().getAllergies().contains(foodId)) {
            throw new RuntimeException("Food already in allergies");
         }else {
            UserProfile profile = userProfile.get();
            profile.getAllergies().add(foodId);
            return userProfileRepository.save(profile);
         }
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public List<String> getFoodAlergies(Authentication authentication) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get().getAllergies();
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public void deleteFoodAlergiesById(Authentication authentication, String foodId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         UserProfile profile = userProfile.get();
         profile.getAllergies().remove(foodId);
         userProfileRepository.save(profile);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public Boolean isFoodAlergies(Authentication authentication, String foodId) {
         
         Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);
   
         Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));
   
         if (userProfile.isPresent()) {
            return userProfile.get().getAllergies().contains(foodId);
         } else {
            throw new RuntimeException("User profile not found");
         }
   }

   @Override
   public UserProfile addUnwantedFoodById(Authentication authentication, String foodId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
            if(userProfile.get().getFavoriteFoods().contains(foodId)) {
               throw new RuntimeException("Food in favorites list");
            }else if(userProfile.get().getAllergies().contains(foodId)) {
               throw new RuntimeException("Food in allergies list");
            }else if(userProfile.get().getUnwantedFoods().contains(foodId)) {
               throw new RuntimeException("Food already in unwanted");
            }else {
               UserProfile profile = userProfile.get();
               profile.getUnwantedFoods().add(foodId);
               return userProfileRepository.save(profile);
            }
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public List<String> getUnwantedFoods(Authentication authentication) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get().getUnwantedFoods();
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public void deleteUnwantedFoodById(Authentication authentication, String foodId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         UserProfile profile = userProfile.get();
         profile.getUnwantedFoods().remove(foodId);
         userProfileRepository.save(profile);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

   @Override
   public Boolean isUnwantedFood(Authentication authentication, String foodId) {

      Map<String, String> userData = jwtService.getClaimsFromAuthentication(authentication);

      Optional<UserProfile> userProfile = userProfileRepository.findByUserId(userData.get(USER_ID));

      if (userProfile.isPresent()) {
         return userProfile.get().getUnwantedFoods().contains(foodId);
      } else {
         throw new RuntimeException("User profile not found");
      }
   }

}
