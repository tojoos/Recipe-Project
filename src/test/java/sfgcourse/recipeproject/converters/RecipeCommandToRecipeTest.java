package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.*;
import sfgcourse.recipeproject.domain.Recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                                              new CategoryCommandToCategory(),
                                              new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        List<IngredientCommand> ingredientCommandSet = new ArrayList<>();
        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setUom(new UnitOfMeasureCommand());
        ingredientCommandSet.add(ingredientCommand1);
        ingredientCommandSet.add(new IngredientCommand());
        recipeCommand.setIngredients(ingredientCommandSet);

        List<CategoryCommand> categoryCommandSet = new ArrayList<>();
        categoryCommandSet.add(new CategoryCommand());
        recipeCommand.setCategories(categoryCommandSet);

        NotesCommand notes = new NotesCommand();
        notes.setId("2");
        recipeCommand.setNotes(notes);

        Recipe savedRecipe = converter.convert(recipeCommand);

        assertNotNull(savedRecipe);
        assertEquals(ingredientCommandSet.size(), savedRecipe.getIngredients().size());
        assertEquals(categoryCommandSet.size(), savedRecipe.getCategories().size());
        assertEquals(recipeCommand.getId(), savedRecipe.getId());
        assertEquals(recipeCommand.getNotes().getId(), savedRecipe.getNotes().getId());

    }
}