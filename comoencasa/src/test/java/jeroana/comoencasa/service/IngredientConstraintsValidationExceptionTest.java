package jeroana.comoencasa.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolationException;
import jeroana.comoencasa.dto.IngredientDTO;

@SpringBootTest
public class IngredientConstraintsValidationExceptionTest {
    @Autowired
    private IngredientServiceImpl ingredientService;

    @Test
    public void constraintsValidationExceptionTest(){
        //prueba nombre nulo
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.saveIngredient(new IngredientDTO(null, "lácteo"));});

        //prueba nombre corto
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.saveIngredient(new IngredientDTO(null, "la"));});
        
        //prueba nombre largo
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.saveIngredient(new IngredientDTO(null, "prueba1prueba2prueba3prueba4"));});
        
        //prueba mes mínimo menor a 1
        IngredientDTO ing1 = new IngredientDTO();
        ing1.setName("Tomate");
        ing1.setFromMonth(0);
        ing1.setToMonth(12);
        ing1.setType("Verdura");
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.saveIngredient(ing1);});

        //prueba mes máximo mayor a 12
        IngredientDTO ing2 = new IngredientDTO();
        ing2.setName("Tomate");
        ing2.setFromMonth(1);
        ing2.setToMonth(13);
        ing2.setType("Verdura");
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.saveIngredient(ing2);});

        // prueba tipo nulo
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.saveIngredient(new IngredientDTO("leche", null));});

        //prueba todo nulo
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.saveIngredient(new IngredientDTO(null, null));});
    }

    @Test
    public void createIngredientOk(){
        IngredientDTO ing = new IngredientDTO();
        ing.setName("Tomate");
        ing.setFromMonth(1);
        ing.setToMonth(12);
        ing.setType("Verdura");
        IngredientDTO ingredientDto = ingredientService.saveIngredient(ing);

        assertNotNull(ingredientDto);

        assertNotNull("Tomate", ingredientDto.getName());

        assertNotNull(ingredientDto.getId());
    }
}
