package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.*;
import sfgcourse.recipeproject.domain.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        Recipe recipe = new Recipe();
        recipe.setId("3");

        Set<Ingredient> ingredientSet = new HashSet<>();
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setUom(new UnitOfMeasure());
        ingredientSet.add(ingredient1);
        ingredientSet.add(new Ingredient());
        recipe.setIngredients(ingredientSet);

        Set<Category> categorySet = new HashSet<>();
        categorySet.add(new Category());
        categorySet.add(new Category());
        recipe.setCategories(categorySet);

        Notes notes = new Notes();
        notes.setId("3");
        recipe.setNotes(notes);

        RecipeCommand savedRecipe = converter.convert(recipe);

        assertNotNull(savedRecipe);
        assertEquals(ingredientSet.size(), savedRecipe.getIngredients().size());
        assertEquals(categorySet.size(), savedRecipe.getCategories().size());
        assertEquals(recipe.getId(), savedRecipe.getId());
        assertEquals(recipe.getNotes().getId(), savedRecipe.getNotes().getId());

    }
}