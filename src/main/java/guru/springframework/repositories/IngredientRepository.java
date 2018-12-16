package guru.springframework.repositories;
/*
 * @author Marecki
 */

import guru.springframework.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
