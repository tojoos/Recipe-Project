package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.NotesCommand;
import sfgcourse.recipeproject.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(2L);
        notes.setRecipeNotes("Notes1");

        NotesCommand nc = converter.convert(notes);

        assertNotNull(nc);
        assertEquals(notes.getId(), nc.getId());
        assertEquals(notes.getRecipeNotes(), nc.getRecipeNotes());
    }
}