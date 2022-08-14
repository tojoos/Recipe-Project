package sfgcourse.recipeproject.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@ToString
@Document
public class Category {

    @Id
    private String id;
    private String categoryName;

    @DBRef
    private List<Recipe> recipes;
}
