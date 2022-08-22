package sfgcourse.recipeproject.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import sfgcourse.recipeproject.domain.Recipe;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
