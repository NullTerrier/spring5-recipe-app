package guru.springframework.repositories;
/*
 * @author Marecki
 */

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.persistence.Id;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

}
