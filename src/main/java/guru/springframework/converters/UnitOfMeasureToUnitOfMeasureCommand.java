package guru.springframework.converters;
/*
 * @author Marecki
 */

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Override
    @Nullable
    @Synchronized
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {

        if (source != null) {
            UnitOfMeasureCommand oumc = new UnitOfMeasureCommand();
            oumc.setDescription(source.getDescription());
            oumc.setId(source.getId());
            return oumc;
        }

        return null;
    }
}
