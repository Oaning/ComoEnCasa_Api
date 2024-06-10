package jeroana.comoencasa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jeroana.comoencasa.model.Ingredient;
import jeroana.comoencasa.model.Recipe;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void get_recipe_withValidParameters_shouldReturn200(){

        Ingredient quesoUntar = new Ingredient("Queso de untar", "Lácteo");
        ingredientRepository.save(quesoUntar);

        Ingredient huevo = new Ingredient("Huevo", "Proteína animal");
        ingredientRepository.save(huevo);

        Ingredient lecheCondensada = new Ingredient("Leche condensada", "Lácteo");
        ingredientRepository.save(lecheCondensada);

        Ingredient nata = new Ingredient("Nata", "Lácteo");
        ingredientRepository.save(nata);

        Recipe tartaQueso = new Recipe();
        tartaQueso.setName("Tarta de queso");

        //tartaQueso.getIngredientsList().add(quesoUntar);
        //tartaQueso.getIngredientsList().add(huevo);
        //tartaQueso.getIngredientsList().add(lecheCondensada);
        //tartaQueso.getIngredientsList().add(nata);

        recipeRepository.save(tartaQueso);

        recipeRepository.flush();

        assertNotNull(tartaQueso);
        assertNotNull(tartaQueso.getId());
        assertEquals("Tarta de queso", tartaQueso.getName());
        assertEquals(tartaQueso.getId(), recipeRepository.findByName("Tarta de queso").getId());
    }
}
