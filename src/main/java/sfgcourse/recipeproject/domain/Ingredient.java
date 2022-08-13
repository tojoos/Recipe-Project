package sfgcourse.recipeproject.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Ingredient {

    @Id
    private String id = UUID.randomUUID().toString();
    private String recipeId;

    private String description;
    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;

    public Ingredient (String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient () {

    }
}
