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
import org.springframework.web.bind.annotation.RestController;

import jeroana.comoencasa.dto.IngredientDTO;
import jeroana.comoencasa.service.IngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    @GetMapping("/{id}")
    public IngredientDTO getIngredient(@PathVariable("id") Long id){
        return ingredientService.getIngredient(id);
    }

    @GetMapping("/all")
    public List<IngredientDTO> getAll(){
        return ingredientService.getAll();
    }

    @PostMapping("/new")
    public IngredientDTO newIngredient(@RequestBody IngredientDTO ingredient){
        return ingredientService.saveIngredient(ingredient);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIngredient(@PathVariable("id") Long id){
        ingredientService.deleteIngredient(id);
    }

    @PutMapping("/update")
    public IngredientDTO updateIngredient(@RequestBody IngredientDTO ingredient){
        return ingredientService.saveIngredient(ingredient);
    }
}
