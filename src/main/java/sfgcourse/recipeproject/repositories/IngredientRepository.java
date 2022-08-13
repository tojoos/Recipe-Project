package sfgcourse.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgcourse.recipeproject.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
