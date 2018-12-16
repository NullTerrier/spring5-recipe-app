package guru.springframework.services;

import com.google.common.collect.Sets;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * @author Marecki
 */

public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository uomRepository;


    private UnitOfMeasureToUnitOfMeasureCommand uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    private IngredientToIngredientCommand ingConverter = new IngredientToIngredientCommand(uomConverter);
    private IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);

    IngredientServiceImpl service;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new IngredientServiceImpl(ingredientRepository,
                recipeRepository,
                ingConverter,
                uomRepository,
                ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(4L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        final Optional<Recipe> optionalRecipe = Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        //then
        final IngredientCommand ingredientCommand = service.findByRecipeIdAndIngredientId(1L, 3L);

        assertNotNull(ingredientCommand);
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());

        verify(recipeRepository, times(1)).findById(anyLong());

    }

    @Test
    public void testSaveRecipeCommandIngredientFound() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(2L);
        command.setId(3L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when

        IngredientCommand savedCommand = service.saveIngredientCommand(command);

        //then

        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void testSaveRecipeCommandIngredientNotFound() {
        //given
        final String DESC = "DESC";
        final long UOM_ID = 4L;

        UnitOfMeasureCommand uomC = new UnitOfMeasureCommand();
        uomC.setId(UOM_ID);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setDescription(DESC);
        ingredientCommand.setAmount(BigDecimal.ONE);
        ingredientCommand.setUom(uomC);

        Recipe savedRecipe = new Recipe();
        Ingredient ing1 = new Ingredient();
        ing1.setId(10L);
        ing1.setDescription("NOT THE ONE");
        ing1.setAmount(BigDecimal.TEN);
        ing1.setUom(uom);

        Ingredient ing2 = new Ingredient();
        ing2.setId(7L);
        ing2.setAmount(BigDecimal.ONE);
        ing2.setDescription(DESC);
        ing2.setUom(uom);

        savedRecipe.addIngredient(ing1);
        savedRecipe.addIngredient(ing2);

        when(recipeRepository.findById(any())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        final IngredientCommand command = service.saveIngredientCommand(ingredientCommand);

        assertNotNull(command);
        assertEquals(Long.valueOf(7), command.getId());
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteIngredientByIdNoRecipe() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        service.deleteIngredientById(1L, 2L);

    }

    @Test(expected = RuntimeException.class)
    public void testDeleteIngredientByIdNoIngredient() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        service.deleteIngredientById(1l, 2L);
    }

    @Test
    public void testDeleteIngredient() {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ing1 = new Ingredient();
        ing1.setId(2L);

        Ingredient ing2 = new Ingredient();
        ing2.setId(3L);

        recipe.addIngredient(ing1);
        recipe.addIngredient(ing2);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        service.deleteIngredientById(1L, 2L);

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

}