package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * @author Marecki
 */

public class NotesToNotesCommandTest {

    private final Long ID = 1L;
    private final String RECIPE_NOTES = "Desc";

    NotesToNotesCommand converter;

    @Before
    public void setUp()  {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void nullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void emptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        final NotesCommand notesCommand = converter.convert(notes);

        assertNotNull(notesCommand);
        assertEquals(notesCommand.getId(), ID);
        assertEquals(notes.getRecipeNotes(), RECIPE_NOTES);

    }
}