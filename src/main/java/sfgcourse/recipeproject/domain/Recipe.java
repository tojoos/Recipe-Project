package sfgcourse.recipeproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String source;
    private String url;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;

    @Lob
    private Byte[] image;
    @Lob
    private String directions;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id") )
    private Set<Category> categories = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.getIngredients().add(ingredient);
        return this;
    }
}
