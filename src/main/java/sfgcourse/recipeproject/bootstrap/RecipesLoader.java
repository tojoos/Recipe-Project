package sfgcourse.recipeproject.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sfgcourse.recipeproject.domain.*;
import sfgcourse.recipeproject.repositories.CategoryRepository;
import sfgcourse.recipeproject.repositories.RecipeRepository;
import sfgcourse.recipeproject.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.*;

@Component
public class RecipesLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipesLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    public List<Recipe> getRecipes() {
        List<Recipe> recipeList = new ArrayList<>();

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByUom("Teaspoon");
        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByUom("Tablespoon");
        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByUom("Cup");
        Optional<UnitOfMeasure> pinchUomOptional = unitOfMeasureRepository.findByUom("Pinch");
        Optional<UnitOfMeasure> ounceUomOptional = unitOfMeasureRepository.findByUom("Ounce");
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByUom("Each");
        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByUom("Dash");
        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByUom("Pint");

        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();
        UnitOfMeasure pinchUom = pinchUomOptional.get();
        UnitOfMeasure ounceUom = ounceUomOptional.get();
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();

        Optional<Category> americanCategoryOption = categoryRepository.findByCategoryName("American");
        Optional<Category> italianCategoryOption = categoryRepository.findByCategoryName("Italian");
        Optional<Category> mexicanCategoryOption = categoryRepository.findByCategoryName("Mexican");
        Optional<Category> fastFoodCategoryOption = categoryRepository.findByCategoryName("Fast Food");

        Category americanCategory = americanCategoryOption.get();
        Category italianCategory = italianCategoryOption.get();
        Category mexicanCategory = mexicanCategoryOption.get();
        Category fastFoodCategory = fastFoodCategoryOption.get();

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setServings(4);
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.getCategories().add(americanCategory);
        perfectGuacamole.getCategories().add(mexicanCategory);
        perfectGuacamole.setSource("simplyrecipes.com");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setNotes(new Notes(perfectGuacamole, "Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards."));
        perfectGuacamole.setDirections("1. Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
                "\n 2. Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n 3. Add remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
                "\n 4. Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.\n" +
                "\n" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");

        perfectGuacamole.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), perfectGuacamole, eachUom));
        perfectGuacamole.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(".5"), perfectGuacamole, teaSpoonUom));
        perfectGuacamole.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), perfectGuacamole, tableSpoonUom));
        perfectGuacamole.getIngredients().add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), perfectGuacamole, tableSpoonUom));
        perfectGuacamole.getIngredients().add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), perfectGuacamole, eachUom));
        perfectGuacamole.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), perfectGuacamole, tableSpoonUom));
        perfectGuacamole.getIngredients().add(new Ingredient("freshly grated black pepper", new BigDecimal(2), perfectGuacamole, dashUom));
        perfectGuacamole.getIngredients().add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), perfectGuacamole, eachUom));

        recipeList.add(perfectGuacamole);

        Recipe chickenTaco = new Recipe();
        chickenTaco.setPrepTime(20);
        chickenTaco.setCookTime(15);
        chickenTaco.setDescription("Spicy Grilled Chicken Tacos");
        chickenTaco.setServings(6);
        chickenTaco.setDifficulty(Difficulty.EASY);
        chickenTaco.getCategories().add(americanCategory);
        chickenTaco.getCategories().add(mexicanCategory);
        chickenTaco.setSource("simplyrecipes.com");
        chickenTaco.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTaco.setNotes(new Notes(chickenTaco, "We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n"));

        chickenTaco.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n");

        chickenTaco.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), chickenTaco, tableSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), chickenTaco, teaSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), chickenTaco, teaSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), chickenTaco, teaSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), chickenTaco, teaSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), chickenTaco, eachUom));
        chickenTaco.getIngredients().add(new Ingredient("finely grated orange zestr", new BigDecimal(1), chickenTaco, tableSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), chickenTaco, tableSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), chickenTaco, tableSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("boneless chicken thighs", new BigDecimal(4), chickenTaco, tableSpoonUom));
        chickenTaco.getIngredients().add(new Ingredient("small corn tortillasr", new BigDecimal(8), chickenTaco, eachUom));
        chickenTaco.getIngredients().add(new Ingredient("packed baby arugula", new BigDecimal(3), chickenTaco, cupUom));
        chickenTaco.getIngredients().add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), chickenTaco, eachUom));
        chickenTaco.getIngredients().add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), chickenTaco, eachUom));
        chickenTaco.getIngredients().add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), chickenTaco, pintUom));
        chickenTaco.getIngredients().add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), chickenTaco, eachUom));
        chickenTaco.getIngredients().add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), chickenTaco, eachUom));
        chickenTaco.getIngredients().add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), chickenTaco, cupUom));
        chickenTaco.getIngredients().add(new Ingredient("lime, cut into wedges", new BigDecimal(4), chickenTaco, eachUom));

        recipeList.add(chickenTaco);

        return recipeList;
    }
}
