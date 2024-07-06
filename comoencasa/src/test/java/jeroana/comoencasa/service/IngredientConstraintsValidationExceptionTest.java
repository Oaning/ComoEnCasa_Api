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
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.newIngredient(new IngredientDTO(null, "lácteo"));});

        //prueba nombre corto
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.newIngredient(new IngredientDTO(null, "la"));});
        
        //prueba nombre largo
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.newIngredient(new IngredientDTO(null, "prueba1prueba2prueba3prueba4"));});

        // prueba tipo nulo
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.newIngredient(new IngredientDTO("leche", null));});

        //prueba todo nulo
        assertThrows(ConstraintViolationException.class, () -> {ingredientService.newIngredient(new IngredientDTO(null, null));});
    }

    @Test
    public void createIngredientOk(){
        IngredientDTO ing = new IngredientDTO();
        ing.setName("Tomate");
        ing.setFromMonth(1);
        ing.setToMonth(12);
        ing.setType("Verdura");
        IngredientDTO ingredientDto = ingredientService.newIngredient(ing);

        assertNotNull(ingredientDto);

        assertNotNull("Tomate", ingredientDto.getName());

        assertNotNull(ingredientDto.getId());
    }
}
