package cl.govegan.msuserresources.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "userProfiles")
public class UserProfile {

   @Id
   private String id;

   @Field(targetType = FieldType.OBJECT_ID)
   private String userId;
   
   private String name;
   private String profilePicture;
   private int age;
   private Gender gender;
   private double weight;
   private double height;
   private String city;
   private String country;
   
   @Field(targetType = FieldType.OBJECT_ID)
   private ArrayList<String> allergies;

   @Field(targetType = FieldType.OBJECT_ID)
   private ArrayList<String> favoriteFoods;

   @Field(targetType = FieldType.OBJECT_ID)
   private ArrayList<String> unwantedFoods;

   @Field(targetType = FieldType.OBJECT_ID)
   private ArrayList<String> favoriteRecipes;
   
   private int caloriesPerDay;
   private double waterPerDay;
   private String title;
}
