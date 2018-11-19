package guru.springframework.services;
/*
 * @author Marecki
 */

import guru.springframework.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
