package sfgcourse.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.converters.RecipeCommandToRecipe;
import sfgcourse.recipeproject.converters.RecipeToRecipeCommand;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.exceptions.NotFoundException;
import sfgcourse.recipeproject.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipeConverter;
    private final RecipeToRecipeCommand recipeToRecipeCommandConverter;


    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipeConverter,
                             RecipeToRecipeCommand recipeToRecipeCommandConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipeConverter = recipeCommandToRecipeConverter;
        this.recipeToRecipeCommandConverter = recipeToRecipeCommandConverter;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(String id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if(recipeOptional.isEmpty()) {
            throw new NotFoundException("For Id value: " + id + " no recipe was found.");
        }
        return recipeOptional.get();
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe convertedRecipe = recipeCommandToRecipeConverter.convert(command);
        assert convertedRecipe != null;
        Recipe savedRecipe = recipeRepository.save(convertedRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());
        return recipeToRecipeCommandConverter.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findCommandById(String id) {
        RecipeCommand recipeCommand = recipeToRecipeCommandConverter.convert(findById(id));

        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
            recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));
        }

        return recipeCommand;
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
