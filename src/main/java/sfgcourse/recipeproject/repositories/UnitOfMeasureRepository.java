package sfgcourse.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import sfgcourse.recipeproject.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByUom(String uom);
}
