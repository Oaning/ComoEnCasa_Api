package jeroana.comoencasa.service;

import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jeroana.comoencasa.dto.RecipeDTO;

@Service
public interface RecipeService {
    public RecipeDTO saveRecipe(@Valid RecipeDTO recipe);

    public RecipeDTO getRecipe(Long id);

    public List<RecipeDTO> getAll();

    public List<RecipeDTO> getRecipesByIngredients(List<String> ingredients);

    public RecipeDTO getRandomRecipe();
    
    public void deleteRecipe(Long id);
}
