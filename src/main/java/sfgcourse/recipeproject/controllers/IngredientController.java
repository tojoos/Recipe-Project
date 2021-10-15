package sfgcourse.recipeproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.exceptions.NotFoundException;
import sfgcourse.recipeproject.services.IngredientService;
import sfgcourse.recipeproject.services.RecipeService;
import sfgcourse.recipeproject.services.UnitOfMeasureService;

@Slf4j
@Controller
@RequestMapping("recipes/{recipeId}/ingredients")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("")
    public String showIngredients(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping({"/{ingredientId}/show", "/{ingredientId}"})
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/{ingredientId}/update")
    public String initUpdateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/new")
    public String initAddNewIngredient(@PathVariable String recipeId, Model model) {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/process")
    public String processSaveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        return "redirect:/recipes/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/{ingredientId}/delete")
    public String deleteIngredientCommandById(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipes/" + recipeId + "/ingredients";
    }
}
