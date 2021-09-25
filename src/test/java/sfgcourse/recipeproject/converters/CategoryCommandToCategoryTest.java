package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.CategoryCommand;
import sfgcourse.recipeproject.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    CategoryCommandToCategory converter;

    @BeforeEach
    public void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setCategoryName("Name1");
        categoryCommand.setId(1L);

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(categoryCommand.getId(), category.getId());
        assertEquals(categoryCommand.getCategoryName(), category.getCategoryName());
    }
}