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
    public Ingredient findByRecipeIdandId(String recipeId, String ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()) {
            Optional<Ingredient> optIngredient = optionalRecipe.get().getIngredients()
                    .stream()
                    .filter(ing -> ing.getId().equals(ingredientId))
                    .findFirst();
            if (optIngredient.isPresent()) {
                Ingredient ingredient = optIngredient.get();
                ingredient.setRecipeId(recipeId);
                return ingredient;
            } else {
                throw new NotFoundException("For recipe id: " + recipeId + ", ingredient id: " + ingredientId +  " was not found.");
            }
        } else {
            throw new NotFoundException("For Id value: " + recipeId + " no recipe was found.");
        }
    }

    @Override
    public IngredientCommand findCommandByRecipeIdandIngredientId(String recipeId, String ingredientId) {
        return ingToIngCommandConverter.convert(findByRecipeIdandId(recipeId, ingredientId));
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){
            //todo toss error if not found!
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setRecipeId(ingredientCommand.getRecipeId());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                Ingredient ingredient = ingCommandToIngConverter.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = Optional.ofNullable(savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Couldn't find new ingredient")));
            }

            //to do check for fail

            //enhance with id value
            IngredientCommand ingredientCommandSaved = ingToIngCommandConverter.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return ingToIngCommandConverter.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteById(String recipeId, String ingredientId) {
        System.out.println("recipe id in delete" + recipeId);
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if(optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                    .filter(ing -> ing.getId().equals(ingredientId))
                    .findFirst();

            if(optionalIngredient.isPresent()) {
                Ingredient ingToDelete = optionalIngredient.get();
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
