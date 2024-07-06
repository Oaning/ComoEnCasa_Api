package jeroana.comoencasa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jeroana.comoencasa.model.Recipe;
//import jeroana.comoencasa.model.UserRecipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
    Recipe findByName(String name);

    //List<Recipe> findByUser(Long userId);

    //SELECT ri FROM RecipeIngredient ri  where ri.ingredient in (SELECT i from Ingredient i WHERE i.id IN (:ids))
    //@Query("SELECT ur FROM UserRecipe ur WHERE ur.recipe in(SELECT r from Recipe r WHERE r.id in :ids)")
    //List<UserRecipe> findByRecipes(@Param("ids") List<Long> ids);

    //@Query("Select r from Recipe r join r.recipeIngredients ri join ri.ingredient i where i.name IN (:ingredients)")
    @Query("SELECT r FROM Recipe r WHERE r.id IN (SELECT ri.recipe.id FROM RecipeIngredient ri WHERE ri.ingredient IN (SELECT i from Ingredient i where i.name IN (:ingredients)))")
    List<Recipe> findByIngredients(@Param("ingredients") List<String> ingredients);
}
