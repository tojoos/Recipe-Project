package sfgcourse.recipeproject.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import sfgcourse.recipeproject.bootstrap.RecipesLoader;
import sfgcourse.recipeproject.domain.UnitOfMeasure;
import sfgcourse.recipeproject.repositories.reactive.CategoryReactiveRepository;
import sfgcourse.recipeproject.repositories.reactive.RecipeReactiveRepository;
import sfgcourse.recipeproject.repositories.reactive.UnitOfMeasureReactiveRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("Disabled until the reactive refactoring is done!")
@DataMongoTest
class UnitOfMeasureRepositoryIntegrationTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        RecipesLoader recipesLoader = new RecipesLoader(recipeRepository, categoryRepository, unitOfMeasureRepository);
        recipesLoader.onApplicationEvent(null);
    }

    @Test
    @DirtiesContext //reload context (cleaning it for next test)
    public void findByUom() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", uomOptional.get().getUom());
        assertNotEquals("Tablespoon", uomOptional.get().getUom());
    }

    @Test
    public void findByUomCup() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByUom("Cup");
        assertEquals("Cup", uomOptional.get().getUom());
    }
}