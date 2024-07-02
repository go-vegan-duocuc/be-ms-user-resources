package cl.govegan.msuserresources.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.govegan.msuserresources.model.UserProfile;
import cl.govegan.msuserresources.services.userprofileservice.UserProfileService;
import cl.govegan.msuserresources.web.request.UserProfileRequest;
import cl.govegan.msuserresources.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/user-resources")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping()
    public ResponseEntity<ApiResponse<UserProfile>> getUserProfile(Authentication authentication) {

        try {
            UserProfile userProfile = userProfileService.getUserProfile(authentication);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.OK.value())
                            .message("User profile retrieved")
                            .data(userProfile)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<UserProfile>> createUserProfile(Authentication authentication,
            @RequestBody UserProfileRequest userProfile) {

        try {
            UserProfile createdProfile = userProfileService.createUserProfile(authentication, userProfile);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.CREATED.value())
                            .message("User profile created")
                            .data(createdProfile)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<UserProfile>> updateUserProfile(Authentication authentication,
            @RequestBody UserProfileRequest userProfile) {

        try {
            UserProfile updatedProfile = userProfileService.updateUserProfile(authentication, userProfile);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.OK.value())
                            .message("User profile updated")
                            .data(updatedProfile)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PatchMapping("/add-favorite-recipe")
    public ResponseEntity<ApiResponse<UserProfile>> addFavoriteRecipe(Authentication authentication,
            @RequestParam String recipeId) {

        try {
            UserProfile updatedProfile = userProfileService.addFavoriteRecipeById(authentication, recipeId);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite recipe added")
                            .data(updatedProfile)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/favorite-recipes")
    public ResponseEntity<ApiResponse<Page<String>>> getFavoriteRecipes(
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        try {
            List<String> favoriteRecipes = userProfileService.getFavoriteRecipes(authentication);
            int start = Math.min(page * size, favoriteRecipes.size());
            int end = Math.min((page + 1) * size, favoriteRecipes.size());

            Pageable pageable = PageRequest.of(page, size);

            Page<String> favoriteRecipesPage = new PageImpl<>(favoriteRecipes.subList(start, end), pageable,
                    favoriteRecipes.size());

            return ResponseEntity.ok(
                    ApiResponse.<Page<String>>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite recipes retrieved")
                            .data(favoriteRecipesPage)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Page<String>>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @DeleteMapping("/delete-favorite-recipe")
    public ResponseEntity<ApiResponse<String>> deleteFavoriteRecipe(Authentication authentication,
            @RequestParam String recipeId) {

        try {
            userProfileService.deleteFavoriteRecipeById(authentication, recipeId);
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite recipe deleted")
                            .data("RecipeId " + recipeId + " deleted")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/is-favorite-recipe")
    public ResponseEntity<ApiResponse<Boolean>> isFavoriteRecipe(Authentication authentication,
            @RequestParam String recipeId) {

        try {
            Boolean isFavorite = userProfileService.isFavoriteRecipe(authentication, recipeId);
            return ResponseEntity.ok(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite recipe retrieved")
                            .data(isFavorite)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PatchMapping("/add-favorite-food")
    public ResponseEntity<ApiResponse<UserProfile>> addFavoriteFood(Authentication authentication,
            @RequestParam String foodId) {

        try {
            UserProfile updatedProfile = userProfileService.addFavoriteFoodById(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite food added")
                            .data(updatedProfile)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/favorite-foods")
    public ResponseEntity<ApiResponse<Page<String>>> getFavoriteFoods(
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        try {
            List<String> favoriteFoods = userProfileService.getFavoriteFoods(authentication);

            int start = Math.min(page * size, favoriteFoods.size());
            int end = Math.min((page + 1) * size, favoriteFoods.size());

            Pageable pageable = PageRequest.of(page, size);
            Page<String> favoriteFoodsPage = new PageImpl<>(favoriteFoods.subList(start, end), pageable,
                    favoriteFoods.size());

            return ResponseEntity.ok(
                    ApiResponse.<Page<String>>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite foods retrieved")
                            .data(favoriteFoodsPage)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Page<String>>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @DeleteMapping("/delete-favorite-food")
    public ResponseEntity<ApiResponse<String>> deleteFavoriteFood(Authentication authentication,
            @RequestParam String foodId) {

        try {
            userProfileService.deleteFavoriteFoodById(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite food deleted")
                            .data("FoodId " + foodId + " deleted")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/is-favorite-food")
    public ResponseEntity<ApiResponse<Boolean>> isFavoriteFood(Authentication authentication,
            @RequestParam String foodId) {

        try {
            Boolean isFavorite = userProfileService.isFavoriteFood(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.OK.value())
                            .message("Favorite food retrieved")
                            .data(isFavorite)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PatchMapping("/add-food-alergies")
    public ResponseEntity<ApiResponse<UserProfile>> addFoodAlergies(Authentication authentication,
            @RequestParam String foodId) {

        try {
            UserProfile updatedProfile = userProfileService.addFoodAlergiesById(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.OK.value())
                            .message("Food alergies added")
                            .data(updatedProfile)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/food-alergies")
    public ResponseEntity<ApiResponse<Page<String>>> getFoodAlergies(
            Authentication authentication,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        try {
            List<String> allergies = userProfileService.getFoodAlergies(authentication);

            int start = Math.min(page * size, allergies.size());
            int end = Math.min((page + 1) * size, allergies.size());

            Pageable pageable = PageRequest.of(page, size);
            Page<String> allergiesPage = new PageImpl<>(allergies.subList(start, end), pageable, allergies.size());

            return ResponseEntity.ok(
                    ApiResponse.<Page<String>>builder()
                            .status(HttpStatus.OK.value())
                            .message("Food alergies retrieved")
                            .data(allergiesPage)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Page<String>>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @DeleteMapping("/delete-food-alergies")
    public ResponseEntity<ApiResponse<String>> deleteFoodAlergies(Authentication authentication,
            @RequestParam String foodId) {

        try {
            userProfileService.deleteFoodAlergiesById(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.OK.value())
                            .message("Food alergies deleted")
                            .data("FoodId " + foodId + " deleted")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/is-food-alergies")
    public ResponseEntity<ApiResponse<Boolean>> isFoodAlergies(Authentication authentication,
            @RequestParam String foodId) {

        try {
            Boolean isFoodAlergies = userProfileService.isFoodAlergies(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.OK.value())
                            .message("Food alergies retrieved")
                            .data(isFoodAlergies)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PatchMapping("/add-unwanted-food")
    public ResponseEntity<ApiResponse<UserProfile>> addUnwantedFood(Authentication authentication,
            @RequestParam String foodId) {

        try {
            UserProfile updatedProfile = userProfileService.addUnwantedFoodById(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.OK.value())
                            .message("Unwanted food added")
                            .data(updatedProfile)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<UserProfile>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/unwanted-foods")
    public ResponseEntity<ApiResponse<List<String>>> getUnwantedFoods(Authentication authentication) {

        try {

            List<String> unwantedFoods = userProfileService.getUnwantedFoods(authentication);
            return ResponseEntity.ok(
                    ApiResponse.<List<String>>builder()
                            .status(HttpStatus.OK.value())
                            .message("Unwanted foods retrieved")
                            .data(unwantedFoods)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<List<String>>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @DeleteMapping("/delete-unwanted-food")
    public ResponseEntity<ApiResponse<String>> deleteUnwantedFood(Authentication authentication,
            @RequestParam String foodId) {

        try {
            userProfileService.deleteUnwantedFoodById(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.OK.value())
                            .message("Unwanted food deleted")
                            .data("FoodId " + foodId + " deleted")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/is-unwanted-food")
    public ResponseEntity<ApiResponse<Boolean>> isUnwantedFood(Authentication authentication,
            @RequestParam String foodId) {

        try {
            Boolean isUnwantedFood = userProfileService.isUnwantedFood(authentication, foodId);
            return ResponseEntity.ok(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.OK.value())
                            .message("Unwanted food retrieved")
                            .data(isUnwantedFood)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<Boolean>builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @DeleteMapping("/delete-user-profile")
        public ResponseEntity<ApiResponse<String>> deleteUserProfile(Authentication authentication) {
        
                try {
                userProfileService.deleteUserProfile(authentication);
                return ResponseEntity.ok(
                        ApiResponse.<String>builder()
                                .status(HttpStatus.OK.value())
                                .message("User profile deleted")
                                .data("User profile deleted")
                                .build());
                } catch (Exception e) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.<String>builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(e.getMessage())
                                .data(null)
                                .build());
                }
        }
}
