package sfgcourse.recipeproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sfgcourse.recipeproject.commands.IngredientCommand;
import sfgcourse.recipeproject.commands.UnitOfMeasureCommand;
import sfgcourse.recipeproject.converters.*;
import sfgcourse.recipeproject.domain.Ingredient;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.repositories.IngredientRepository;
import sfgcourse.recipeproject.repositories.RecipeRepository;
import sfgcourse.recipeproject.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;


    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
    }

    @Test
    void testFindCommandByRecipeIdandIngredientId() {
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

    @Test
    void testSaveIngredientCommand() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertNotNull(savedIngredientCommand);
        assertEquals(ingredientCommand.getId(), savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testDeleteById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(2L);

        Ingredient ing1 = new Ingredient();
        ing1.setId(1L);
        Ingredient ing2 = new Ingredient();
        ing2.setId(2L);
        Ingredient ing3 = new Ingredient();
        ing3.setId(3L);

        recipe.getIngredients().add(ing1);
        recipe.getIngredients().add(ing2);
        recipe.getIngredients().add(ing3);


        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        ingredientService.deleteById(2L, 3L);

        //then
        assertEquals(2L, recipeRepository.findById(2L).get().getIngredients().size());
        verify(recipeRepository, times(2)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}