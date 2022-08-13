package sfgcourse.recipeproject.services;

import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.domain.Ingredient;

public interface IngredientService {
    Ingredient findByRecipeIdandId(String recipeId, String ingredientId);
    IngredientCommand findCommandByRecipeIdandIngredientId(String recipeId, String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteById(String recipeId, String ingredientId);
}
