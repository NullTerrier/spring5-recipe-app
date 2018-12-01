package guru.springframework.repositories;
/*
 * @author Marecki
 */

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Set<Recipe> findAll();
}
