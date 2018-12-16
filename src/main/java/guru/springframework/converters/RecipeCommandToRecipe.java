package guru.springframework.converters;
/*
 * @author Marecki
 */

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingConverter;
    private final CategoryCommandToCategory catConverter;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, IngredientCommandToIngredient ingConverter, CategoryCommandToCategory catConverter) {
        this.notesConverter = notesConverter;
        this.ingConverter = ingConverter;
        this.catConverter = catConverter;
    }

    @Nullable
    @Synchronized
    @Override
    public Recipe convert(@Nullable RecipeCommand source) {

        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();

        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setCookTime(source.getCookTime());
        recipe.setUrl(source.getUrl());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredientCommand -> recipe.getIngredients().add(ingConverter.convert(ingredientCommand)));
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(categoryCommand -> recipe.getCategories().add(catConverter.convert(categoryCommand)));
        }

        return recipe;
    }
}
