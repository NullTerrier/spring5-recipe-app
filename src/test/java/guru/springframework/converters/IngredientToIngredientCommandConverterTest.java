package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
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
public class IngredientToIngredientCommandConverterTest {

    private final Long INGREDIENT_ID = 1L;
    private final String INGREDIENT_DESCRIPTION = "desc";
    private final BigDecimal INGREDIENT_AMOUNT = BigDecimal.ONE;
    private final Long UOM_ID = 2L;
    private final Long RECIPE_ID = 3L;
    private Recipe recipe;
    private UnitOfMeasure unitOfMeasure;
    private UnitOfMeasureCommand unitOfMeasureCommand;

    @Mock
    UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @InjectMocks
    IngredientToIngredientCommandConverter converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(uomConverter);
    }

    @Test
    public void convert() {
        //given
        recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);


        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        ingredient.setUom(unitOfMeasure);
        ingredient.setDescription(INGREDIENT_DESCRIPTION);
        ingredient.setAmount(INGREDIENT_AMOUNT);
        ingredient.setId(INGREDIENT_ID);

        //when
        when(uomConverter.convert(unitOfMeasure)).thenReturn(unitOfMeasureCommand);
        final IngredientCommand ingredientCommand = converter.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertEquals(ingredientCommand.getId(), INGREDIENT_ID);
        assertEquals(ingredientCommand.getAmount(), INGREDIENT_AMOUNT);
        assertEquals(ingredientCommand.getDescription(), INGREDIENT_DESCRIPTION);
        assertEquals(ingredientCommand.getRecipeId(), recipe.getId());
        assertNotNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(ingredientCommand.getUnitOfMeasure().getId(), unitOfMeasure.getId());
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new Ingredient()));

    }
}