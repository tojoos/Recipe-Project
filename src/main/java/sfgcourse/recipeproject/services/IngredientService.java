package sfgcourse.recipeproject.services;

import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.domain.Ingredient;

public interface IngredientService {
    Ingredient findByRecipeIdandId(Long recipeId, Long ingredientId);
    IngredientCommand findCommandByRecipeIdandIngredientId(Long recipeId, Long ingredientId);
}