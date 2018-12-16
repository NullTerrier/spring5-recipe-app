package guru.springframework.converters;

import com.sun.org.apache.regexp.internal.RE;
import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * @author Marecki
 */

public class NotesCommandToNotesTest {

    private final Long ID = 1L;
    private final String RECIPE_NOTES = "Notes";

    NotesCommandToNotes converter;

    @Before
    public void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void nullArgument() {
        assertNull(converter.convert(null));
    }

    @Test
    public void emptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() {

        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setRecipeNotes(RECIPE_NOTES);
        notesCommand.setId(ID);

        //when
        final Notes notes = converter.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());

    }
}