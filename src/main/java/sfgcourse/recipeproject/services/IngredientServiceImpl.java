package sfgcourse.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.converters.IngredientCommandToIngredient;
import sfgcourse.recipeproject.converters.IngredientToIngredientCommand;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.exceptions.NotFoundException;
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

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingToIngCommandConverter, IngredientCommandToIngredient ingCommandToIngConverter) {
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
                throw new NotFoundException("For recipe id: " + recipeId + ", ingredient id: " + ingredientId +  " was not found.");
            }
        } else {
            throw new NotFoundException("For Id value: " + recipeId + " no recipe was found.");
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
            if(ingredientCommand.getId() == null) {
                long maxId = 1;
                for(Recipe r : recipeRepository.findAll()) {
                    for(Ingredient ing : r.getIngredients()) {
                        if(ing.getId() >= maxId)
                            maxId = ing.getId();
                    }
                }
                ingredientCommand.setId(maxId + 1);
            }

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

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                    .filter(ing -> ing.getId().equals(ingredientId))
                    .findFirst();

            if(optionalIngredient.isPresent()) {
                Ingredient ingToDelete = optionalIngredient.get();
                ingToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingToDelete);
                recipeRepository.save(recipe);
            } else {
                throw new NotFoundException("Couldn't delete ingredient id: " + ingredientId + "\n For recipe id: " + recipeId + ", ingredient was not found.");
            }
        } else {
            throw new NotFoundException("Couldn't delete ingredient id: " + ingredientId + "\n For Id value: " + recipeId + ", no recipe was found.");
        }
    }
}
