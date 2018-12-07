package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * @author Marecki
 */

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private Long ID = 1L;
    private String DESCRIPTION = "Measure";

    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp()  {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }


    @Test
    public void convert() {

        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        //when
        final UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasureCommand);

        assertNotNull(unitOfMeasure);
        assertEquals(unitOfMeasure.getId(), ID);
        assertEquals(unitOfMeasure.getDescription(), DESCRIPTION);

    }
}