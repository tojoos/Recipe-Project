package sfgcourse.recipeproject.commands;

import org.hibernate.validator.constraints.URL;
import sfgcourse.recipeproject.domain.Difficulty;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor
public class RecipeCommand {
    private String id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;
    private String source;

    @URL
    private String url;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;
    private Byte[] image;

    @NotBlank
    private String directions;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
    private Difficulty difficulty;
}
