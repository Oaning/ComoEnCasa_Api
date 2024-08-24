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
        Ingredient tomate = new Ingredient();
        tomate.setName("Tomate");
        tomate.setFromMonth(1);
        tomate.setToMonth(12);
        tomate.setType("Verdura");
        ingredientRepository.save(tomate);

        ingredientRepository.flush();
        
        assertNotNull(tomate);
        assertNotNull(tomate.getId());
        assertEquals("Tomate", tomate.getName());
        assertEquals(tomate.getId(), ingredientRepository.findByName("Tomato").getId());
    }
}
