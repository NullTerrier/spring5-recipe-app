package guru.springframework.repositories;
/*
 * @author Marecki
 */

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;


import javax.persistence.Id;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Id> {

    Optional<Category> findByDescription(String description);



}
