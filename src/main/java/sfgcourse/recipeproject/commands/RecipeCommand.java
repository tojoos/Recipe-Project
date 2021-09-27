package sfgcourse.recipeproject.commands;

import sfgcourse.recipeproject.domain.Difficulty;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private String source;
    private String url;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private Byte[] image;
    private String directions;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
    private Difficulty difficulty;
}
