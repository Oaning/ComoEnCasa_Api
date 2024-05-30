package jeroana.comoencasa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jeroana.comoencasa.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
    Ingredient findByName (String name);

    @Query("Select i from Ingredient i where i.id in :ids")
    List<Ingredient> findByIngredients(@Param("ids") List<Long> ids);
}
