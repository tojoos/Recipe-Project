package sfgcourse.recipeproject.repositories.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sfgcourse.recipeproject.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    void saveCategory() {
        Category category = new Category();
        category.setCategoryName("categoryName1");

        categoryReactiveRepository.save(category).block();

        assertEquals(1, categoryReactiveRepository.count().block());
    }

    @Test
    void findAllCategories() {
        Category category1 = new Category();
        category1.setCategoryName("categoryName1");
        Category category2 = new Category();
        category2.setCategoryName("categoryName2");

        categoryReactiveRepository.save(category1).block();
        categoryReactiveRepository.save(category2).block();

        assertEquals(2, categoryReactiveRepository.count().block());
    }

    @Test
    void deleteCategory() {
        Category category1 = new Category();
        category1.setCategoryName("categoryName1");
        Category category2 = new Category();
        category2.setCategoryName("categoryName2");

        categoryReactiveRepository.save(category1).block();
        categoryReactiveRepository.save(category2).block();

        assertEquals(2, categoryReactiveRepository.count().block());

        categoryReactiveRepository.delete(category2).block();
        assertEquals(1, categoryReactiveRepository.count().block());

        assertNull(categoryReactiveRepository.findByCategoryName(category2.getCategoryName()).block());
    }

    @Test
    void findByCategoryName() {
        Category category1 = new Category();
        category1.setCategoryName("categoryName1");
        Category category2 = new Category();
        category2.setCategoryName("categoryName2");

        categoryReactiveRepository.save(category1).block();
        categoryReactiveRepository.save(category2).block();

        assertNotNull(categoryReactiveRepository.findByCategoryName(category2.getCategoryName()).block());
    }
}