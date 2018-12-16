package guru.springframework.services;

import com.google.common.collect.Sets;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * @author Marecki
 */

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    UnitOfMeasureService unitOfMeasureService;

    @Before

    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, uomConverter);
    }

    @Test
    public void listAllUoms() {

        //given
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(2L);

        final Set<UnitOfMeasure> unitOfMeasures = Sets.newHashSet(unitOfMeasure1, unitOfMeasure2);


        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        //then

        final Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

        assertNotNull(unitOfMeasureCommands);
        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();

    }
}