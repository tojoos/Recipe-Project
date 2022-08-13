package sfgcourse.recipeproject.commands;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String RecipeId;

    @NotBlank
    private String description;

    @DecimalMin("0.1")
    @DecimalMax("1000")
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
}
