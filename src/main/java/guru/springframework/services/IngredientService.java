package guru.springframework.services;
/*
 * @author Marecki
 */

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

public interface IngredientService {

    Ingredient findById(Long id);

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteIngredientById(Long recipeId, Long ingredientId);
}
