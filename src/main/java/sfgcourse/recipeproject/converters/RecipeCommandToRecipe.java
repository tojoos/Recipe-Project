package sfgcourse.recipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesCommandToNotesConverter;
    private final CategoryCommandToCategory categoryCommandToCategoryConverter;
    private final IngredientCommandToIngredient ingredientCommandToIngredientConverter;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotesConverter,
                                 CategoryCommandToCategory categoryCommandToCategoryConverter,
                                 IngredientCommandToIngredient ingredientCommandToIngredientConverter) {
        this.notesCommandToNotesConverter = notesCommandToNotesConverter;
        this.categoryCommandToCategoryConverter = categoryCommandToCategoryConverter;
        this.ingredientCommandToIngredientConverter = ingredientCommandToIngredientConverter;
    }

    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null)
            return null;
        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDirections(source.getDirections());
        recipe.setNotes(notesCommandToNotesConverter.convert(source.getNotes()));
        recipe.setDifficulty(source.getDifficulty());
        recipe.setUrl(source.getUrl());
        recipe.setImage(source.getImage());
        recipe.setSource(source.getSource());
        recipe.setServings(source.getServings());
        recipe.setDescription(source.getDescription());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());

        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(cat -> recipe.getCategories().add(categoryCommandToCategoryConverter.convert(cat)));
        }

        if(source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientCommandToIngredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}
