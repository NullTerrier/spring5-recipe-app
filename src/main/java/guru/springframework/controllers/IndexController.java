package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jt on 6/1/17.
 */
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){

        Set<Recipe> recipes = recipeService.getRecipes();

        model.addAttribute("recipes", recipes);

        return "index";
    }

    @RequestMapping({"/recipes"})
    public String getRecipesPage(Model model) {
        Set<Recipe> recipes = recipeService.getRecipes();

        model.addAttribute("recipes", recipes);
        return "recipes";
    }
}
