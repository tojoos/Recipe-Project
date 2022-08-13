package sfgcourse.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgcourse.recipeproject.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
