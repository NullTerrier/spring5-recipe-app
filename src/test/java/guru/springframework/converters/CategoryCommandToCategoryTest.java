package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * @author Marecki
 */

public class CategoryCommandToCategoryTest {

    private Long ID = 1L;
    private String DESCRIPTION = "Category";

    private CategoryCommandToCategory converter;

    @Before
    public void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void emptyObjectConvert() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void nullObjectConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {

        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        final Category category = converter.convert(categoryCommand);

        //then
        assertNotNull(category);
        assertEquals(category.getId(), ID);
        assertEquals(category.getDescription(), DESCRIPTION);

    }
}