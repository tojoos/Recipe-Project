package sfgcourse.recipeproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.services.RecipeService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/{id}/show", "/{id}"})
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String initUpdateRecipeForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @PostMapping("/process")
    public String processSaveOrUpdateRecipeForm(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult result) {

        if(result.hasErrors()) {
            result.getAllErrors().forEach(err -> log.debug(err.toString()));

            return "recipe/recipeform";
        }

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipes/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipeById(@PathVariable String id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
