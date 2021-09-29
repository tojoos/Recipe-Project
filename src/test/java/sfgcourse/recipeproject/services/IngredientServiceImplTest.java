package sfgcourse.recipeproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.converters.IngredientToIngredientCommand;
import sfgcourse.recipeproject.converters.RecipeCommandToRecipe;
import sfgcourse.recipeproject.converters.RecipeToRecipeCommand;
import sfgcourse.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.repositories.RecipeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
    }

    @Test
    void findCommandByRecipeIdandIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(4L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdandIngredientId(4L, 2L);

        assertEquals(2L,ingredientCommand.getId());
        assertEquals(4L,ingredientCommand.getRecipeId());

        verify(recipeRepository, times(1)).findById(anyLong());
    }
}