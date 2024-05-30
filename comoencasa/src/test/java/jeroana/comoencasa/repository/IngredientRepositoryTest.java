package jeroana.comoencasa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jeroana.comoencasa.model.Ingredient;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class IngredientRepositoryTest {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void get_ingredient_withValidParameters_shouldReturn200(){
        Ingredient tomato = new Ingredient();
        tomato.setName("Tomato");
        tomato.setFromMonth(1);
        tomato.setToMonth(12);
        tomato.setType("Verdura");
        ingredientRepository.save(tomato);

        ingredientRepository.flush();
        
        assertNotNull(tomato);
        assertNotNull(tomato.getId());
        assertEquals("Tomato", tomato.getName());
        assertEquals(tomato.getId(), ingredientRepository.findByName("Tomato").getId());
    }
}
