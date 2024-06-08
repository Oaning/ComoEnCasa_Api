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
import jeroana.comoencasa.service.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @GetMapping("/{id}")
    public RecipeDTO getRecipe(@PathVariable("id") Long id){
        return recipeService.getRecipe(id);
    }

    @GetMapping("/all")
    public List<RecipeDTO> getAll(){
        return recipeService.getAll();
    }

    @GetMapping("/ingredients")
    public List<RecipeDTO> getRecipesByIngredients(@RequestParam("ingredients") List<String> ingredients){
        return recipeService.getRecipesByIngredients(ingredients);
    }

    @GetMapping("/random")
    public RecipeDTO getRandomRecipe(){
        return recipeService.getRandomRecipe();
    }

    @PostMapping("/new")
    public RecipeDTO newRecipe(@RequestBody RecipeDTO recipe){
        return recipeService.saveRecipe(recipe);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable("id") Long id){
        recipeService.deleteRecipe(id);
    }

    @PutMapping("/update")
    public RecipeDTO updateRecipe(@RequestBody RecipeDTO recipe){
        return recipeService.saveRecipe(recipe);
    }
}
