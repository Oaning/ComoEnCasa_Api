package jeroana.comoencasa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolationException;
import jeroana.comoencasa.dto.IngredientDTO;
import jeroana.comoencasa.dto.RecipeDTO;

@SpringBootTest
public class RecipeConstraintsValidationExceptionTest {
    @Autowired
    private RecipeServiceImpl recipeService;

    @Autowired
    private IngredientServiceImpl ingredientService;

    @Test
    public void constraintsValidationExceptionTest(){
        //prueba listaIngredientes nula
        RecipeDTO rec1 = new RecipeDTO();
        rec1.setName("Pan");
        rec1.setIngredientsList(null);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.saveRecipe(rec1);});

        List<IngredientDTO> listaIngredientes = new ArrayList<>();
        //prueba listaIngredientes vacía
        RecipeDTO rec2 = new RecipeDTO();
        rec2.setName("Pan");
        rec2.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.saveRecipe(rec2);});

        IngredientDTO pan = new IngredientDTO("Pan", "Grano");
        listaIngredientes.add(pan);
        
        //prueba nombre nulo
        RecipeDTO rec3 = new RecipeDTO();
        rec3.setName(null);
        rec3.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.saveRecipe(rec3);});
        
        //prueba nombre muy corto
        RecipeDTO rec4 = new RecipeDTO();
        rec4.setName("ol");
        rec4.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.saveRecipe(rec4);});

        //prueba nombre muy largo
        RecipeDTO rec5 = new RecipeDTO();
        rec5.setName("prueba12345prueba12345prueba12345prueba12345prueba12345");
        rec5.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.saveRecipe(rec5);});

    }

    @Test
    public void createRecipeOk(){
        IngredientDTO ingredientDto;
        List<IngredientDTO> listaIngredientes = new ArrayList<>();
        IngredientDTO huevo = new IngredientDTO("Huevo", "Proteína animal");
        ingredientDto = ingredientService.saveIngredient(huevo);
        listaIngredientes.add(ingredientDto);
        IngredientDTO leche = new IngredientDTO("Leche", "Proteína animal");
        ingredientDto = ingredientService.saveIngredient(leche);
        listaIngredientes.add(ingredientDto);
        IngredientDTO pan = new IngredientDTO("Pan", "Grano");
        ingredientDto = ingredientService.saveIngredient(pan);
        listaIngredientes.add(ingredientDto);

        RecipeDTO rec = new RecipeDTO();
        rec.setName("Torrijas");
        rec.setIngredientsList(listaIngredientes);
        RecipeDTO recipeDto = recipeService.saveRecipe(rec);

        assertNotNull(recipeDto);

        assertEquals("Torrijas", recipeDto.getName());

        assertNotNull(recipeDto.getIngredientsList());
    }
}
