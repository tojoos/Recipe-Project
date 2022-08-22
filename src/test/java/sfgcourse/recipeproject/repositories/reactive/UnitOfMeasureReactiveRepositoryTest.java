package sfgcourse.recipeproject.repositories.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sfgcourse.recipeproject.domain.UnitOfMeasure;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    void saveUom() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setUom("Some uom");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        assertEquals(1, unitOfMeasureReactiveRepository.count().block());
        assertNotNull(unitOfMeasureReactiveRepository.findByUom(unitOfMeasure.getUom()));
    }

    @Test
    void deleteUom() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setUom("Another Uom");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
        unitOfMeasureReactiveRepository.delete(unitOfMeasure).block();

        assertEquals(0, unitOfMeasureReactiveRepository.count().block());
    }

    @Test
    void findAllUom() {
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setUom("Some uom1");
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setUom("Some uom2");
        UnitOfMeasure unitOfMeasure3 = new UnitOfMeasure();
        unitOfMeasure3.setUom("Some uom3");

        List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();
        unitOfMeasureList.add(unitOfMeasure1);
        unitOfMeasureList.add(unitOfMeasure2);
        unitOfMeasureList.add(unitOfMeasure3);

        unitOfMeasureReactiveRepository.saveAll(unitOfMeasureList).blockFirst();

        assertEquals(3, unitOfMeasureReactiveRepository.count().block());
    }
}