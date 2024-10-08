package jeroana.comoencasa.service;

import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.RecipeResponseDTO;

@Service
public interface RecipeService {
    public RecipeResponseDTO newRecipe(@Valid RecipeDTO recipe);

    public void newAdminRecipe(@Valid RecipeDTO recipe);

    public void updateRecipe(@Valid RecipeDTO recipe);

    public RecipeResponseDTO getRecipe(Long id);

    public RecipeResponseDTO getRecipeByName(String recipeName);

    public List<RecipeResponseDTO> getAll();

    public List<RecipeDTO> getAllRecipes();

    public List<RecipeResponseDTO> getRecipesByIngredients(List<String> ingredients);

    public RecipeResponseDTO getRandomRecipe();
    
    public void deleteRecipe(Long id);
}
