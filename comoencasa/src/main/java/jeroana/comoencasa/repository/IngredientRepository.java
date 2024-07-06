package jeroana.comoencasa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jeroana.comoencasa.model.Ingredient;
import jeroana.comoencasa.model.RecipeIngredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
    Ingredient findByName (String name);

    @Query("SELECT ri FROM RecipeIngredient ri  where ri.ingredient in (SELECT i from Ingredient i WHERE i.id IN (:ids))")
    List<RecipeIngredient> findByIngredients(@Param("ids") List<Long> ids);
}
