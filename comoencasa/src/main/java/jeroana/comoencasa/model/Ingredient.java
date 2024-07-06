package jeroana.comoencasa.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Ingredient {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 25, nullable = false)
    private String name;

    @Column(nullable = true)
    private int fromMonth;

    @Column(nullable = true)
    private int toMonth;

    @Column(length = 25, nullable = false)
    private String type;
    
    //@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<RecipeIngredient> recipeIngredientList = new ArrayList<RecipeIngredient>();

    public Ingredient(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
