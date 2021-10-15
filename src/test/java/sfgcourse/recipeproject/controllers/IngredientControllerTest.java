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
import sfgcourse.recipeproject.exceptions.NotFoundException;
import sfgcourse.recipeproject.services.IngredientService;
import sfgcourse.recipeproject.services.RecipeService;
import sfgcourse.recipeproject.services.UnitOfMeasureService;

import java.util.HashSet;

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
        mockMvc = MockMvcBuilders
                .standaloneSetup(ingredientController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void testShowIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipes/1/ingredients"))
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
        mockMvc.perform(get("/recipes/1/ingredients/1/show"))
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
        mockMvc.perform(get("/recipes/1/ingredients/1/update"))
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
        mockMvc.perform(post("/recipes/3/ingredients/process")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description", "description example"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/3/ingredients/5/show"));
    }



    @Test
    void testDeleteIngredient() throws Exception {
        //then
        mockMvc.perform(get("/recipes/1/ingredients/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/1/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
    }

    @Test
    void testGetIngredientNotFoundException() throws Exception {
        when(ingredientService.findCommandByRecipeIdandIngredientId(anyLong(), anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipes/2/ingredients/99/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error404Page"));
    }

    @Test
    void testGetIngredientNumberFormatException() throws Exception {
        mockMvc.perform(get("/recipes/1/ingredients/xyz/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error400Page"));
    }
}