package sfgcourse.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.converters.IngredientCommandToIngredient;
import sfgcourse.recipeproject.converters.IngredientToIngredientCommand;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.repositories.RecipeRepository;
import sfgcourse.recipeproject.repositories.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;
    IngredientToIngredientCommand ingToIngCommandConverter;
    IngredientCommandToIngredient ingCommandToIngConverter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientToIngredientCommand ingToIngCommandConverter, IngredientCommandToIngredient ingCommandToIngConverter) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingToIngCommandConverter = ingToIngCommandConverter;
        this.ingCommandToIngConverter = ingCommandToIngConverter;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                    .stream()
                    .filter(ing -> ing.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(optionalIngredient.isPresent()) {
                Ingredient existingIngredient = optionalIngredient.get();
                existingIngredient.setDescription(ingredientCommand.getDescription());
                existingIngredient.setAmount(ingredientCommand.getAmount());
                existingIngredient.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("Uom not found")));
            } else {
                recipe.addIngredient(Objects.requireNonNull(ingCommandToIngConverter.convert(ingredientCommand)));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return ingToIngCommandConverter.convert(savedRecipe.getIngredients()
                    .stream()
                    .filter(ing -> ing.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Couldn't find new ingredient")));

        } else {
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }
    }


}
