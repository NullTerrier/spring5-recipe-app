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
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesConverter;
    private final IngredientToIngredientCommand ingConverter;
    private final CategoryToCategoryCommand catConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand notesConverter, IngredientToIngredientCommand ingConverter, CategoryToCategoryCommand catConverter) {
        this.notesConverter = notesConverter;
        this.ingConverter = ingConverter;
        this.catConverter = catConverter;
    }

    @Nullable
    @Synchronized
    @Override
    public RecipeCommand convert(@Nullable Recipe source) {

        if (source == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().stream()
                    .forEach(category -> recipeCommand.getCategories().add(catConverter.convert(category)));


        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients().stream()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingConverter.convert(ingredient)));
        }
        return recipeCommand;
    }
}
