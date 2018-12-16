package guru.springframework.services;
/*
 * @author Marecki
 */

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.uomRepository = uomRepository;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(uomRepository.findAll()
                    .spliterator(), false)
                    .map(converter::convert)
                    .collect(Collectors.toSet());

    }
}
