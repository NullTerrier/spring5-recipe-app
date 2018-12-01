package guru.springframework.domain;
/*
 * @author Marecki
 */

import lombok.*;

import javax.persistence.*;


@EqualsAndHashCode(exclude = {"recipe"})
@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}
