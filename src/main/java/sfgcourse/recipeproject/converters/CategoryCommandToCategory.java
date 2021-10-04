package sfgcourse.recipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import sfgcourse.recipeproject.commands.CategoryCommand;
import sfgcourse.recipeproject.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if(source == null)
            return null;
        final Category category = new Category();
        category.setId(source.getId());
        category.setCategoryName(source.getCategoryName());
        return category;
    }
}
