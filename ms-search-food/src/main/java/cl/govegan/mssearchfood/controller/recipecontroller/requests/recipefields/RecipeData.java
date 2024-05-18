package cl.govegan.mssearchfood.controller.recipecontroller.requests.recipefields;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeData {
   private String difficulty;
   private String yield;
   private String preparationTime;
   private String cookingTime;
}
