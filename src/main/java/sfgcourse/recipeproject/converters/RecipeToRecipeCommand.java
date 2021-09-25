package sfgcourse.recipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.domain.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesToNotesCommandConverter;
    private final CategoryToCategoryCommand categoryToCategoryCommandConverter;
    private final IngredientToIngredientCommand ingredientToIngredientCommandConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommandConverter,
                                 CategoryToCategoryCommand categoryToCategoryCommandConverter,
                                 IngredientToIngredientCommand ingredientToIngredientCommandConverter) {
        this.notesToNotesCommandConverter = notesToNotesCommandConverter;
        this.categoryToCategoryCommandConverter = categoryToCategoryCommandConverter;
        this.ingredientToIngredientCommandConverter = ingredientToIngredientCommandConverter;
    }

    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null)
            return null;
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setNotes(notesToNotesCommandConverter.convert(source.getNotes()));
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(cat -> recipeCommand.getCategories().add(categoryToCategoryCommandConverter.convert(cat)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommandConverter.convert(ingredient)));
        }

        return recipeCommand;
    }
}