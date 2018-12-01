package guru.springframework.domain;
/*
 * @author Marecki
 */

import lombok.*;

import javax.persistence.*;


@Data
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ingredient ingredient;

    private String description;

}
