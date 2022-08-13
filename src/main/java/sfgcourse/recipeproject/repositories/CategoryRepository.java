package sfgcourse.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgcourse.recipeproject.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findByCategoryName(String categoryName);
}
