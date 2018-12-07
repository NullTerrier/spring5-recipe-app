package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * @author Marecki
 */

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    final Long ID = 1L;
    final String DESCRIPTION = "Measure";


    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {

        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {

        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);

        //when
        final UnitOfMeasureCommand unitOfMeasureCommand = converter.convert(unitOfMeasure);

        //then
        assertNotNull(unitOfMeasureCommand);
        assertEquals(unitOfMeasureCommand.getId(), ID);
        assertEquals(unitOfMeasureCommand.getDescription(), DESCRIPTION);





    }
}