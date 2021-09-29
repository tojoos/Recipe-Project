package sfgcourse.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.converters.IngredientToIngredientCommand;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.repositories.RecipeRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    IngredientToIngredientCommand ingToIngCommandConverter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingToIngCommandConverter) {
        this.recipeRepository = recipeRepository;
        this.ingToIngCommandConverter = ingToIngCommandConverter;
    }

    @Override
    public Ingredient findByRecipeIdandId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()) {
            Optional<Ingredient> optIngredient = optionalRecipe.get().getIngredients()
                    .stream()
                    .filter(ing -> ing.getId().equals(ingredientId))
                    .findFirst();
            if (optIngredient.isPresent()) {
                return optIngredient.get();
            } else {
                log.error("Ingredient id not found: " + ingredientId);
                return null;
            }
        } else {
            log.error("Recipe id not found: " + recipeId);
            return null;
        }
    }

    @Override
    public IngredientCommand findCommandByRecipeIdandIngredientId(Long recipeId, Long ingredientId) {
        return ingToIngCommandConverter.convert(findByRecipeIdandId(recipeId, ingredientId));
    }
}
