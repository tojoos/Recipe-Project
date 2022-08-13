package sfgcourse.recipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfgcourse.recipeproject.commands.NotesCommand;
import sfgcourse.recipeproject.domain.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    NotesCommandToNotes converter;

    @BeforeEach()
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setRecipeNotes("Some notes");
        notesCommand.setId("1");

        Notes savedNotes = converter.convert(notesCommand);

        assertNotNull(savedNotes);
        assertEquals(notesCommand.getId(), savedNotes.getId());
        assertEquals(notesCommand.getRecipeNotes(), savedNotes.getRecipeNotes());
    }
}