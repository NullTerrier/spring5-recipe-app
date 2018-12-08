package guru.springframework.converters;
/*
 * @author Marecki
 */

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommandConverter implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommandConverter(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Override
    @Nullable
    public IngredientCommand convert(Ingredient source) {

        if (source == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();
        command.setAmount(source.getAmount());
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        if (source.getRecipe() != null) {
            command.setRecipeId(source.getRecipe().getId());
        }
        command.setUnitOfMeasure(uomConverter.convert(source.getUom()));
        return command;
    }
}
