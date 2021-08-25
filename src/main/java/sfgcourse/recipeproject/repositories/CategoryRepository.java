package sfgcourse.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgcourse.recipeproject.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
