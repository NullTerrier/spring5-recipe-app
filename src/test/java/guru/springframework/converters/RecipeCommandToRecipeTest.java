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

import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/*
 * @author Marecki
 */

@RunWith(MockitoJUnitRunner.class)
public class RecipeCommandToRecipeTest {

    @Mock
    IngredientCommandToIngredient ingConverter;
    @Mock
    NotesCommandToNotes notesConverter;
    @Mock
    CategoryCommandToCategory catConverter;

    @InjectMocks
    RecipeCommandToRecipe converter;

    private final Long ID = 1L;
    private final String DESC = "Desc";
    private final Integer PREP_TIME = 1;
    private final Integer COOK_TIME = 2;
    private final Integer SERVINGS = 3;
    private final String SOURCE = "Source";
    private final String URL = "URL";
    private final String DIRECTIONS = "Direcitions";
    private Set<IngredientCommand> ingredients;
    private final Difficulty DIFFICULTY = Difficulty.EASY;
    private NotesCommand notesCommand;
    private Set<CategoryCommand> categories;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(ingConverter);
        MockitoAnnotations.initMocks(notesConverter);
        MockitoAnnotations.initMocks(catConverter);
    }

    @Test
    public void nullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void emptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
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

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);

        Category category1 = new Category();
        category1.setId(CAT_ID1);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID2);

        ingredients = Sets.newHashSet(ingredientCommand1, ingredientCommand2);
        categories = Sets.newHashSet(categoryCommand1, categoryCommand2);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESC);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setNotes(notesCommand);
        recipeCommand.setUrl(URL);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setIngredients(ingredients);
        recipeCommand.setCategories(categories);
        recipeCommand.setDirections(DIRECTIONS);

        //when
        when(notesConverter.convert(notesCommand)).thenReturn(notes);
        when(catConverter.convert(categoryCommand1)).thenReturn(category1);
        when(catConverter.convert(categoryCommand2)).thenReturn(category2);
        when(ingConverter.convert(ingredientCommand1)).thenReturn(ingredient1);
        when(ingConverter.convert(ingredientCommand2)).thenReturn(ingredient2);

        final Recipe recipe = converter.convert(recipeCommand);

        assertNotNull(recipe);
        assertNotNull(recipe.getCategories());
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getNotes());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(ID, recipe.getId());
        assertEquals(DESC, recipe.getDescription());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(URL, recipe.getUrl());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(DIFFICULTY.toString(), recipe.getDifficulty().toString());
        assertEquals(DIRECTIONS, recipe.getDirections());


    }
}