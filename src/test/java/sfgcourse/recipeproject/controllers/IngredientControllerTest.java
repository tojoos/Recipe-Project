package sfgcourse.recipeproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.UnitOfMeasure;
import sfgcourse.recipeproject.services.IngredientService;
import sfgcourse.recipeproject.services.RecipeService;
import sfgcourse.recipeproject.services.UnitOfMeasureService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class IngredientControllerTest {

    IngredientController ingredientController;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void testShowIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void testShowIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findCommandByRecipeIdandIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));

        //then
        verify(ingredientService, times(1)).findCommandByRecipeIdandIngredientId(anyLong(), anyLong());
    }

    @Test
    void testUpdateIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.findCommandByRecipeIdandIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("uomList", "ingredient"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));
    }

    @Test
    void testSaveOrUpdateIngredient() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(5L);
        command.setRecipeId(3L);

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/3/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description", "description example"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3/ingredient/5/show"));
    }



    @Test
    void testDeleteIngredient() throws Exception {
        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
    }
}