package sfgcourse.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sfgcourse.recipeproject.domain.Category;
import sfgcourse.recipeproject.domain.UnitOfMeasure;
import sfgcourse.recipeproject.repositories.CategoryRepository;
import sfgcourse.recipeproject.repositories.RecipeRepository;
import sfgcourse.recipeproject.repositories.UnitOfMeasureRepository;
import sfgcourse.recipeproject.services.RecipeService;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    private final RecipeService recipeService;


    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index"})
    public String getIndexPage(Model model) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUom("Tablespoon");

        System.out.println("Category ID: " + categoryOptional.get().getId() + " UoM ID: " + unitOfMeasureOptional.get().getId());

        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
