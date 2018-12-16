package guru.springframework.converters;
/*
 * @author Marecki
 */

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Override
    @Nullable
    public Notes convert(@Nullable NotesCommand source) {

        if (source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setRecipeNotes(source.getRecipeNotes());
        notes.setId(source.getId());

        return notes;
    }
}
