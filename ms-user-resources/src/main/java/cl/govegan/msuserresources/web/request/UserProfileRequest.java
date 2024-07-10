package cl.govegan.msuserresources.web.request;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {

   private String userId;
   private String email;
   private String name;
   private String profilePicture;
   private int age;
   private String gender;
   private double weight;
   private double height;
   private String city;
   private String country;
   private ArrayList<String> allergies;
   private ArrayList<String> favoriteFoods;
   private ArrayList<String> unwantedFoods;
   private ArrayList<String> favoriteRecipes;
   private int caloriesPerDay;
   private double waterPerDay;
   private String title;

}
