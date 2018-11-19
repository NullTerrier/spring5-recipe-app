package guru.springframework.bootstrap;
/*
 * @author Marecki
 */

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecipes() {


        List<Recipe> recipes = new ArrayList<>(2);

        UnitOfMeasure each = checkIfUomPresentInDB("Each");
        UnitOfMeasure tablespoon = checkIfUomPresentInDB("Tablespoon");
        UnitOfMeasure teaspoon = checkIfUomPresentInDB("Teaspoon");
        UnitOfMeasure cup = checkIfUomPresentInDB("Cup");
        UnitOfMeasure pinch = checkIfUomPresentInDB("Pinch");
        UnitOfMeasure ounce = checkIfUomPresentInDB("Ounce");
        UnitOfMeasure dash = checkIfUomPresentInDB("Dash");
        Category american = checkIfCategoryPresentInDB("American");
        Category italian = checkIfCategoryPresentInDB("Italian");
        Category mexican = checkIfCategoryPresentInDB("Mexican");
        Category fastFood = checkIfCategoryPresentInDB("Fast Food");

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.getCategories().add(mexican);
        perfectGuacamole.getCategories().add(italian);

        perfectGuacamole.setDescription("Perfect Guacamole");

        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setPrepTime(30);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setSource("Simply Delicious website");
        perfectGuacamole.setDifficulty(Difficulty.EASY);

        perfectGuacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNote = new Notes();
        guacNote.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");

        perfectGuacamole.setNotes(guacNote);
        perfectGuacamole.setUrl("implyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");


        perfectGuacamole.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), each));
        perfectGuacamole.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        perfectGuacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoon));
        perfectGuacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoon));
        perfectGuacamole.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), each));
        perfectGuacamole.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon));
        perfectGuacamole.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash));
        perfectGuacamole.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), each));


        recipes.add(perfectGuacamole);

        return recipes;

    }

    private UnitOfMeasure checkIfUomPresentInDB(String uomDesc) {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription(uomDesc);

        if (!uomOptional.isPresent()) {
            throw new RuntimeException(uomDesc + " not found");
        }

        return  uomOptional.get();
    }

    private Category checkIfCategoryPresentInDB(String categoryDesc) {

        Optional<Category> categoryOptional = categoryRepository.findByDescription(categoryDesc);

        if (!categoryOptional.isPresent()) {
            throw new RuntimeException(categoryDesc + " not found");
        }

        return categoryOptional.get();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }
}
