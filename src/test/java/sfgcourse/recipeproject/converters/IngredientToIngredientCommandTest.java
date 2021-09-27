package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    IngredientToIngredientCommand converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convertNotNullUom() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setDescription("Desc");
        ingredient.setId(3L);
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(2L);
        ingredient.setUom(uom);

        //when
        IngredientCommand savedIngredient = converter.convert(ingredient);

        //then
        assertEquals(ingredient.getId(), savedIngredient.getId());
        assertEquals(ingredient.getDescription(), savedIngredient.getDescription());

        assertNotNull(savedIngredient);
        assertNotNull(savedIngredient.getUom());

        assertEquals(uom.getId(), savedIngredient.getUom().getId());
    }

    @Test
    void convertNullUom() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setDescription("Desc");
        ingredient.setId(3L);
        ingredient.setUom(null);

        //when
        IngredientCommand savedIngredient = converter.convert(ingredient);

        //then
        assertEquals(ingredient.getId(), savedIngredient.getId());
        assertEquals(ingredient.getDescription(), savedIngredient.getDescription());

        assertNotNull(savedIngredient);
        assertNull(savedIngredient.getUom());

    }
}