package jeroana.comoencasa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jeroana.comoencasa.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
    Recipe findByName(String name);

    @Query("Select r from Recipe r where r.id in :ids")
    List<Recipe> findByRecipes(@Param("ids") List<Long> ids);

    @Query("Select r from Recipe r join r.ingredientsList i where i.name IN (:ingredients)")
    List<Recipe> findByIngredients(@Param("ingredients") List<String> ingredients);
}
