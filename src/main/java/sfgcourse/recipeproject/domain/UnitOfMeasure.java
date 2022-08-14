package sfgcourse.recipeproject.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.UUID;

@Getter @Setter
@Document
public class UnitOfMeasure {

    @Id
    private String id = UUID.randomUUID().toString();
    private String uom;
}
