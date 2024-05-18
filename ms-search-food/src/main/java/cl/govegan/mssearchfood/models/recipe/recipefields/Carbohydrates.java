package cl.govegan.mssearchfood.models.recipe.recipefields;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carbohydrates {
   private String kcal;
   private String gr;
   private String percent;
}
