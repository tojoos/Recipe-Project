package sfgcourse.recipeproject.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import sfgcourse.recipeproject.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitOfMeasureRepositoryIntegrationTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
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