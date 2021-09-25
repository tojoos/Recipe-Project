package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.CategoryCommand;
import sfgcourse.recipeproject.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Name1");

        //when
        CategoryCommand savedCategoryCommand = converter.convert(category);

        //then
        assertEquals(category.getId(), savedCategoryCommand.getId());
        assertEquals(category.getCategoryName(), savedCategoryCommand.getCategoryName());
    }
}