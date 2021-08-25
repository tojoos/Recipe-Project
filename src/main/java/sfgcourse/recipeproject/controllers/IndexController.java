package sfgcourse.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sfgcourse.recipeproject.domain.Category;
import sfgcourse.recipeproject.domain.UnitOfMeasure;
import sfgcourse.recipeproject.repositories.CategoryRepository;
import sfgcourse.recipeproject.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","index"})
    public String getIndexPage() {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUom("Tablespoon");

        System.out.println("Category ID: " + categoryOptional.get().getId() + " UoM ID: " + unitOfMeasureOptional.get().getId());
        return "index";
    }
}
