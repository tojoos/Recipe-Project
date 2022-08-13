package sfgcourse.recipeproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sfgcourse.recipeproject.commands.RecipeCommand;
import sfgcourse.recipeproject.converters.RecipeCommandToRecipe;
import sfgcourse.recipeproject.converters.RecipeToRecipeCommand;
import sfgcourse.recipeproject.domain.Recipe;
import sfgcourse.recipeproject.exceptions.NotFoundException;
import sfgcourse.recipeproject.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipeConverter;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommandConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipeConverter, recipeToRecipeCommandConverter);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeHashSet = new HashSet<>();
        recipeHashSet.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeHashSet);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void findRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById("1");

        assertNotNull(recipeReturned);

        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeByIdTestNotFound() {
        Optional<Recipe> emptyOpt = Optional.empty();
        when(recipeRepository.findById(anyString())).thenReturn(emptyOpt);
        NotFoundException ex = assertThrows(NotFoundException.class, () -> recipeService.findById("2"), "Expected doThing() to throw, but it didn't");
        assertEquals("For Id value: 2 no recipe was found.", ex.getMessage());
    }

    @Test
    void findRecipeCommandById() {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(any())).thenReturn(optionalRecipe);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeToRecipeCommandConverter.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandFound = recipeService.findCommandById("1");

        assertNotNull(commandFound);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void deleteByIdTest() {
        recipeService.deleteById("2");
        verify(recipeRepository, times(1)).deleteById(anyString());
    }
}