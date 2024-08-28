package jeroana.comoencasa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.RecipeResponseDTO;
import jeroana.comoencasa.service.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @GetMapping("/{id}")
    public RecipeResponseDTO getRecipe(@PathVariable("id") Long id){
        return recipeService.getRecipe(id);
    }

    @GetMapping("/get/{name}")
    public RecipeResponseDTO getRecipeByName(@PathVariable("name") String recipeName){
        return recipeService.getRecipeByName(recipeName);
    }

    @GetMapping("/all")
    public List<RecipeResponseDTO> getAll(){
        return recipeService.getAll();
    }

    @GetMapping("/allRecipes")
    public List<RecipeDTO> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @GetMapping("/ingredients")
    public List<RecipeResponseDTO> getRecipesByIngredients(@RequestParam("ingredients") List<String> ingredients){
        return recipeService.getRecipesByIngredients(ingredients);
    }

    @GetMapping("/random")
    public RecipeResponseDTO getRandomRecipe(){
        return recipeService.getRandomRecipe();
    }

    @PostMapping("/new")
    public RecipeResponseDTO newRecipe(@RequestBody RecipeDTO recipe){
        return recipeService.newRecipe(recipe);
    }

    @PostMapping("/newRecipe")
    public void newAdminRecipe(@RequestBody RecipeDTO recipe){
        recipeService.newAdminRecipe(recipe);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable("id") Long id){
        recipeService.deleteRecipe(id);
    }

    @PutMapping("/update")
    public void updateRecipe(@RequestBody RecipeDTO recipe){
        recipeService.updateRecipe(recipe);
    }
}
