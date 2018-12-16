package guru.springframework.converters;

import com.google.common.collect.Sets;
import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import java.util.Set;

import static org.mockito.Mockito.when;

/*
 * @author Marecki
 */

@RunWith(MockitoJUnitRunner.class)
public class RecipeToRecipeCommandTest {

    @Mock
    CategoryToCategoryCommand catConverter;

//    UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @Mock
    IngredientToIngredientCommand ingConverter;

    @Mock
    NotesToNotesCommand noteConverter;

    @InjectMocks
    RecipeToRecipeCommand converter;

    private final Long ID = 1L;
    private final String DESC = "Desc";
    private final Integer PREP_TIME = 1;
    private final Integer COOK_TIME = 2;
    private final Integer SERVINGS = 3;
    private final String SOURCE = "Source";
    private final String URL = "URL";
    private final String DIRECTIONS = "Direcitions";
    private Set<Ingredient> ingredients;
    private final Difficulty DIFFICULTY = Difficulty.EASY;
    private Notes notes;
    private Set<Category> categories;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(catConverter);
        MockitoAnnotations.initMocks(ingConverter);
        MockitoAnnotations.initMocks(noteConverter);
    }

    @Test
    public void nullArgument() {
        assertNull(converter.convert(null));
    }

    @Test
    public void emptyArgument() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {

        //given
        final Long ING_ID1 = 21L;
        final Long ING_ID2 = 22L;
        final Long NOTES_ID = 30L;
        final Long CAT_ID1 = 41L;
        final Long CAT_ID2 = 42L;

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ING_ID1);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(ING_ID1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ING_ID2);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(ING_ID2);

        notes = new Notes();
        notes.setId(NOTES_ID);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);

        Category category1 = new Category();
        category1.setId(CAT_ID1);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID2);

        ingredients = Sets.newHashSet(ingredient1, ingredient2);
        categories = Sets.newHashSet(category1, category2);

        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESC);
        recipe.setSource(SOURCE);
        recipe.setServings(SERVINGS);
        recipe.setPrepTime(PREP_TIME);
        recipe.setNotes(notes);
        recipe.setUrl(URL);
        recipe.setCookTime(COOK_TIME);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setIngredients(ingredients);
        recipe.setCategories(categories);
        recipe.setDirections(DIRECTIONS);

        //when
        when(noteConverter.convert(notes)).thenReturn(notesCommand);
        when(catConverter.convert(category1)).thenReturn(categoryCommand1);
        when(catConverter.convert(category2)).thenReturn(categoryCommand2);
        when(ingConverter.convert(ingredient1)).thenReturn(ingredientCommand1);
        when(ingConverter.convert(ingredient2)).thenReturn(ingredientCommand2);

        final RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertNotNull(recipeCommand);
        assertNotNull(recipeCommand.getCategories());
        assertNotNull(recipeCommand.getIngredients());
        assertNotNull(recipeCommand.getNotes());
        assertEquals(2, recipeCommand.getIngredients().size());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(ID, recipeCommand.getId());
        assertEquals(DESC, recipeCommand.getDescription());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(DIFFICULTY.toString(), recipeCommand.getDifficulty().toString());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());

    }
}