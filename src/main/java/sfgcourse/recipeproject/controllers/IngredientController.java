package sfgcourse.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.services.IngredientService;
import sfgcourse.recipeproject.services.RecipeService;
import sfgcourse.recipeproject.services.UnitOfMeasureService;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String showIngredients(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String saveIngredientCommand(@PathVariable String recipeId, Model model) {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredientCommandById(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
