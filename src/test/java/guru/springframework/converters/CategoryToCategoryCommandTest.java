package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * @author Marecki
 */

public class CategoryToCategoryCommandTest {


    private final Long ID = 1L;
    private final String DESCRIPTION = "Category";
    private CategoryToCategoryCommand converter;

    @Before
    public void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void convertNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void convert() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        //when
        final CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertNotNull(categoryCommand);
        assertEquals(categoryCommand.getId(), ID);
        assertEquals(categoryCommand.getDescription(), DESCRIPTION);

    }
}