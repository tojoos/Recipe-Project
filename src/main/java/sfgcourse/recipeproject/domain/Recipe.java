package sfgcourse.recipeproject.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Document
public class Recipe {

    @Id
    private String id;

    private String description;
    private String source;
    private String url;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private Byte[] image;
    private String directions;
    private Difficulty difficulty;
    private Notes notes;

    private List<Ingredient> ingredients = new ArrayList<>();

    @DBRef
    private List<Category> categories = new ArrayList<>();

    public void setNotes(Notes notes) {
        if(notes != null) {
            this.notes = notes;
        }
    }

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipeId(this.id);
        this.getIngredients().add(ingredient);
    }

    public void addAllIngredients(List<Ingredient> ingredients) {
        if(ingredients != null && !ingredients.isEmpty()) {
            ingredients.forEach(i -> i.setRecipeId(this.id));
            this.getIngredients().addAll(ingredients);
        }
    }

    public void addCategory(Category category) {
        this.getCategories().add(category);
    }
}
