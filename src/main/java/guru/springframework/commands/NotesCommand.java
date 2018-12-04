package guru.springframework.commands;
/*
 * @author Marecki
 */

import guru.springframework.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {

    private Long id;

    private Recipe recipe;

    private String recipeNotes;
}
