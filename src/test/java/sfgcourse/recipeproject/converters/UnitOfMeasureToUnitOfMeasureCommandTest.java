package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {


    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId("3");
        unitOfMeasure.setUom("Some uom2");

        UnitOfMeasureCommand savedUom = converter.convert(unitOfMeasure);

        assertNotNull(savedUom);
        assertEquals(unitOfMeasure.getId(), savedUom.getId());
        assertEquals(unitOfMeasure.getUom(), savedUom.getUom());

    }
}