package sfgcourse.recipeproject.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Getter @Setter
@ToString
@Entity
public class Notes {

    @Id
    private String id;

    private String recipeNotes;

    public Notes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }

    public Notes() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notes notes = (Notes) o;
        return id != null && Objects.equals(id, notes.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
