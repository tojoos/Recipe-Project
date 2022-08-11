package sfgcourse.recipeproject.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sfgcourse.recipeproject.domain.Category;
import sfgcourse.recipeproject.domain.UnitOfMeasure;
import sfgcourse.recipeproject.repositories.CategoryRepository;
import sfgcourse.recipeproject.repositories.UnitOfMeasureRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Profile({"dev","prod"})
public class MySQLBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public MySQLBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (categoryRepository.count() == 0L) {
            log.debug("Loading Categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L) {
            log.debug("Loading Categories");
            loadUnitsOfMeasure();
        }
    }

    private void loadCategories() {
        List<Category> categoryList = new ArrayList<>();

        Category cat1 = new Category();
        cat1.setCategoryName("American");
        categoryList.add(cat1);

        Category cat2 = new Category();
        cat2.setCategoryName("Italian");
        categoryList.add(cat2);

        Category cat3 = new Category();
        cat3.setCategoryName("Mexican");
        categoryList.add(cat3);

        Category cat4 = new Category();
        cat4.setCategoryName("Fast Food");
        categoryList.add(cat4);

        categoryRepository.saveAll(categoryList);
    }

    private void loadUnitsOfMeasure() {
        List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("Teaspoon");
        unitOfMeasureList.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("Tablespoon");
        unitOfMeasureList.add(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("Cup");
        unitOfMeasureList.add(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setUom("Pinch");
        unitOfMeasureList.add(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setUom("Ounce");
        unitOfMeasureList.add(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setUom("Each");
        unitOfMeasureList.add(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setUom("Dash");
        unitOfMeasureList.add(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setUom("Pint");
        unitOfMeasureList.add(uom8);

        unitOfMeasureRepository.saveAll(unitOfMeasureList);
    }
}
