package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    IngredientCommandToIngredient converter;

    @BeforeEach
    public void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("2");
        ingredientCommand.setDescription("Some info");
        UnitOfMeasureCommand unit = new UnitOfMeasureCommand();
        unit.setUom("some Uom");
        ingredientCommand.setUom(unit);

        Ingredient savedIngredient = converter.convert(ingredientCommand);

        assertEquals(ingredientCommand.getId(), savedIngredient.getId());
        assertEquals(ingredientCommand.getDescription(), savedIngredient.getDescription());

        assertNotNull(savedIngredient);
        assertNotNull(savedIngredient.getUom());
    }

    @Test
    void convertWithNullOum() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("2");
        ingredientCommand.setDescription("Some info");
        ingredientCommand.setUom(null);

        Ingredient savedIngredient = converter.convert(ingredientCommand);

        assertEquals(ingredientCommand.getId(), savedIngredient.getId());
        assertEquals(ingredientCommand.getDescription(), savedIngredient.getDescription());

        assertNotNull(savedIngredient);
        assertNull(savedIngredient.getUom());
    }
}