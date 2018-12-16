package guru.springframework.services;
/*
 * @author Marecki
 */

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {


    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository uomRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 UnitOfMeasureRepository uomRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.uomRepository = uomRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Ingredient findById(Long id) {

        final Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);

        if (!ingredientOptional.isPresent()) {
            throw new RuntimeException("No Ingredient was found!");
        }
        return ingredientOptional.get();
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("Recipe id not found. Id: " + recipeId);
        }

        final Recipe recipe = recipeOptional.get();

        final Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient not found. ID: " + ingredientId);
        }


        return ingredientCommandOptional.get();
    }


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        final Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id: ", command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(uomRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("Uom Not found")));
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }


            Recipe savedRecipe = recipeRepository.save(recipe);

            //it is about command!
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()) {

                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

        }

    }

    @Override
    public void deleteIngredientById(Long recipeId, Long ingredientId) {

        final Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (!optionalRecipe.isPresent()) {
            log.debug("No Recipe found, id: " + recipeId);
            throw new RuntimeException("No Recipe with id: " + recipeId);
        }

        Recipe recipe = optionalRecipe.get();

        final Optional<Ingredient> optionalIngredient = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if(!optionalIngredient.isPresent()) {
            log.debug("No Ingredient found on recipe id: " + recipeId + "with ingredient id: " + ingredientId);
            throw new RuntimeException("No Ingredient with id: " + ingredientId);
        } else {
            log.debug("Ingredient deleted id: " + ingredientId);
            Ingredient ingredientToDelete = optionalIngredient.get();
            ingredientToDelete.setRecipe(null);
            recipe.getIngredients().remove(ingredientToDelete);
            recipeRepository.save(recipe);
        }

    }

}
