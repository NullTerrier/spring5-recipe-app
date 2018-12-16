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
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Override
    @Nullable
    public IngredientCommand convert(@Nullable Ingredient source) {

        if (source == null) {
            return null;
        }

        final IngredientCommand command = new IngredientCommand();
        command.setAmount(source.getAmount());
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        if (source.getRecipe() != null) {
            command.setRecipeId(source.getRecipe().getId());
        }
        command.setUom(uomConverter.convert(source.getUom()));
        return command;
    }
}
