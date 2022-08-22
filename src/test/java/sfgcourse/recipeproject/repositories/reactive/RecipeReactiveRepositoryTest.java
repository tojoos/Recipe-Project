package sfgcourse.recipeproject.repositories.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sfgcourse.recipeproject.domain.Recipe;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class RecipeReactiveRepositoryTest {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    void saveRecipe() {
        Recipe recipe = new Recipe();

        recipeReactiveRepository.save(recipe).block();

        assertEquals(1, recipeReactiveRepository.count().block());
    }

    @Test
    void deleteRecipe() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();

        recipeReactiveRepository.save(recipe1).block();
        recipeReactiveRepository.save(recipe2).block();

        assertEquals(2, recipeReactiveRepository.count().block());

        recipeReactiveRepository.delete(recipe2).block();

        assertEquals(1, recipeReactiveRepository.count().block());
    }

    @Test
    void findByIdRecipe() {
        Recipe recipe1 = new Recipe();
        recipe1.setId("new id");
        Recipe recipe2 = new Recipe();
        recipe2.setId("other id");

        recipeReactiveRepository.save(recipe1).block();
        recipeReactiveRepository.save(recipe2).block();

        assertNotNull(recipeReactiveRepository.findById(recipe1.getId()).block());
        assertEquals(recipe1.getId(), recipeReactiveRepository.findById(recipe1.getId()).block().getId());
    }

    @Test
    void findAllRecipe() {
        Recipe recipe1 = new Recipe();
        recipe1.setId("new id");
        Recipe recipe2 = new Recipe();
        recipe2.setId("other id");

        recipeReactiveRepository.save(recipe1).block();
        recipeReactiveRepository.save(recipe2).block();

        assertEquals(2, Objects.requireNonNull(recipeReactiveRepository.findAll().collectList().share().block()).size());
    }
}