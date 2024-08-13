package jeroana.comoencasa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jakarta.validation.ConstraintViolationException;
import jeroana.comoencasa.dto.IngredientDTO;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.RecipeIngredientDTO;
import jeroana.comoencasa.dto.RecipeResponseDTO;

@SpringBootTest
public class RecipeConstraintsValidationExceptionTest {
    @Autowired
    private RecipeServiceImpl recipeService;

    @Test
    @Rollback
    public void constraintsValidationExceptionTest(){
        //prueba listaIngredientes nula
        RecipeDTO rec1 = new RecipeDTO();
        rec1.setName("Pan");
        rec1.setIngredientsList(null);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.newRecipe(rec1);});

        List<RecipeIngredientDTO> listaIngredientes = new ArrayList<>();
        //prueba listaIngredientes vacía
        RecipeDTO rec2 = new RecipeDTO();
        rec2.setName("Pan");
        rec2.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.newRecipe(rec2);});

        IngredientDTO pan = new IngredientDTO("Pan", "Grano");
        RecipeIngredientDTO ri = new RecipeIngredientDTO();
        ri.setIngredient(pan.getName());
        ri.setQuantity("8 rebanadas");
        listaIngredientes.add(ri);
        
        //prueba nombre nulo
        RecipeDTO rec3 = new RecipeDTO();
        rec3.setName(null);
        rec3.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.newRecipe(rec3);});
        
        //prueba nombre muy corto
        RecipeDTO rec4 = new RecipeDTO();
        rec4.setName("ol");
        rec4.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.newRecipe(rec4);});

        //prueba nombre muy largo
        RecipeDTO rec5 = new RecipeDTO();
        rec5.setName("prueba12345prueba12345prueba12345prueba12345prueba12345");
        rec5.setIngredientsList(listaIngredientes);
        assertThrows(ConstraintViolationException.class, () -> {recipeService.newRecipe(rec5);});

    }

    @Test
    @Rollback
    public void createRecipeOk(){
        RecipeDTO recipeDto = new RecipeDTO();
        recipeDto.setName("Torrijas");
        recipeDto.setPhoto("https://ucce22bca14f44016e499dc64e53.previews.dropboxusercontent.com/p/thumb/ACQ2dfecevHHsgpCu3fmTWAfm0_dXRyMWTeB4fJv_FKYE7E9_oAmA0UeQVazqAhjE4m8PP419cn3mLv5jkwhk6JwsvnaBlHidcvM1xvwRsm2n2ZTEb-sL90xylUEc3HmIGawUWciY0f-eOVMXTpzLizu8U1a75aKPrNBNIHTAK2wxN1zeSJPASKzix1VrTbFHw2fKPP8PbDCKRDLrPa-tUNIdNH1o0ytXOw5kkvXhGrkcU96JwNXAMFCoKVSZiGIbqrL8ij63GMf10zzO9eWgZMpHEK0E_7pIolaGcXTpN9yUNkT7hS7tRK2xK5loAu0Hj90J7nRnd2O8rLtUzeZTwU8RrMLIA0m4usi15j7dNQltcdwDmkt-tRdijPz2N4NLX1i-ux2Jlu6nA4L3Y1ZTzkb/p.jpeg");
        recipeDto.setDescription("Infusionar la leche con canela, las pieles de naranja y de limón y el azúcar. Dejar enfriar y echar el pan. Poner a calentar una sartén con abundante aceite de oliva. En un bol batir los huevos, pasar el pan por ambas caras, y freír hasta que se dore al gusto por ambos lados.");
        List<RecipeIngredientDTO> ingredientDtoList = new ArrayList<>();
        RecipeIngredientDTO recipeIngredientDto = new RecipeIngredientDTO();
        recipeIngredientDto.setIngredient("Huevos");
        recipeIngredientDto.setQuantity("2");
        ingredientDtoList.add(recipeIngredientDto);
        recipeIngredientDto.setIngredient("Leche");
        recipeIngredientDto.setQuantity("1 litro");
        ingredientDtoList.add(recipeIngredientDto);
        recipeIngredientDto.setIngredient("Pan");
        recipeIngredientDto.setQuantity("8 rebanadas");
        ingredientDtoList.add(recipeIngredientDto);

        recipeDto.setIngredientsList(ingredientDtoList);
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO();
        recipeResponse.setName(recipeDto.getName());
        recipeResponse.setPhoto(recipeDto.getPhoto());
        recipeResponse.setDescription(recipeDto.getDescription());
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Huevo - 2");
        ingredientsList.add("Leche - 1 litro");
        ingredientsList.add("Pan - 8 rebanadas");

        recipeResponse.setIngredientsList(ingredientsList);
        
        recipeResponse = recipeService.newRecipe(recipeDto);
        
        assertNotNull(recipeDto);
        
        assertEquals("Torrijas", recipeDto.getName());

        assertNotNull(recipeDto.getIngredientsList());
    }
}
