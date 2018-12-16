package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * @author Marecki
 */

@RunWith(MockitoJUnitRunner.class)
public class IngredientCommandToIngredientTest {

    private final Long COMM_ID = 1L;
    private final Long UOM_ID = 2L;
    private final Long RECIPE_ID = 3L;
    private final String DESC = "Desc";
    private final BigDecimal AMOUNT = BigDecimal.ONE;

    @Mock
    UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @InjectMocks
    IngredientCommandToIngredient converter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(uomConverter);
    }

    @Test
    public void convert() {
        //given

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);

        IngredientCommand command = new IngredientCommand();
        command.setId(COMM_ID);
        command.setDescription(DESC);
        command.setUom(unitOfMeasureCommand);
        command.setAmount(AMOUNT);
        command.setRecipeId(RECIPE_ID);

        //when
        when(uomConverter.convert(any())).thenReturn(unitOfMeasure);
        final Ingredient ingredient = converter.convert(command);
        //then

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertNotNull(ingredient.getRecipe());
        assertEquals(ingredient.getId(), COMM_ID);
        assertEquals(ingredient.getAmount(), AMOUNT);
        assertEquals(ingredient.getDescription(), DESC);
        assertEquals(ingredient.getUom().getId(), UOM_ID);
        assertEquals(ingredient.getRecipe().getId(), RECIPE_ID);

    }

    @Test
    public void nullIngredient() {
        assertNull(converter.convert(null));
    }

    @Test
    public void emptyIngredient() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

}