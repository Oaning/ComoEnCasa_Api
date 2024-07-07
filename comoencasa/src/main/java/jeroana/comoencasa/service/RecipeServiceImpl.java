package jeroana.comoencasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.RecipeIngredientDTO;
import jeroana.comoencasa.dto.RecipeResponseDTO;
import jeroana.comoencasa.model.Ingredient;
import jeroana.comoencasa.model.Recipe;
import jeroana.comoencasa.model.RecipeIngredient;
import jeroana.comoencasa.repository.IngredientRepository;
import jeroana.comoencasa.repository.RecipeIngredientRepository;
import jeroana.comoencasa.repository.RecipeRepository;

@Service
@Validated
public class RecipeServiceImpl implements RecipeService{
    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private IngredientRepository ingredientRepo;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public RecipeResponseDTO newRecipe(@Valid RecipeDTO recipeDto) {
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO();
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        Recipe recipeExists = recipeRepo.findByName(recipeDto.getName());
        if(recipeDto.getId() == null && recipeExists == null){
            recipe = recipeRepo.save(recipe);
            List<RecipeIngredientDTO> ingredientsList = recipeDto.getIngredientsList();
            for(RecipeIngredientDTO riDto : ingredientsList){
                addIngredientToRecipe(recipe.getId(), riDto);
            }
            recipeResponse = recipeToRecipeResponseDTO(recipe);
        }

        return recipeResponse;
    }
    
    @Transactional
    private void addIngredientToRecipe(Long id, RecipeIngredientDTO recipeIngredientDto) {
        Recipe recipe = recipeRepo.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
        Ingredient ingredient = ingredientRepo.findById(recipeIngredientDto.getIngredient_id()).orElseThrow(() -> new RuntimeException("Ingredient not found"));
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(recipeIngredientDto.getQuantity());

        recipeIngredientRepo.save(recipeIngredient);
    }

    @Transactional
    public RecipeResponseDTO getRecipe(Long id) {
        RecipeResponseDTO recipeResponse = null;
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if(recipe != null){
            recipeResponse = recipeToRecipeResponseDTO(recipe);
        }
        return recipeResponse;
    }
    
    @Transactional
    public RecipeResponseDTO updateRecipe(RecipeDTO recipeDto){
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO();

        Recipe recipe = recipeRepo.findById(recipeDto.getId()).orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setPhoto(recipeDto.getPhoto());

        List<RecipeIngredient> recipeList = new ArrayList<>();
        for(RecipeIngredientDTO ri : recipeDto.getIngredientsList()){
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            Ingredient ingredient = ingredientRepo.findById(ri.getIngredient_id()).orElse(null);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setQuantity(ri.getQuantity());
            
            recipeIngredientRepo.save(recipeIngredient);
            
            recipeList.add(recipeIngredient);
        }
        recipe.setRecipeIngredientList(recipeList);
        recipe = recipeRepo.save(recipe);
        
        recipeResponse = recipeToRecipeResponseDTO(recipe);
        return recipeResponse;
    }

    @Transactional
    public List<RecipeResponseDTO> getAll() {
        List<Recipe> recipeList = recipeRepo.findAll();
        List<RecipeResponseDTO> recipeResponseList= new ArrayList<>();
        for(Recipe recipe: recipeList){
            RecipeResponseDTO recipeResponse = recipeToRecipeResponseDTO(recipe);
            recipeResponseList.add(recipeResponse);
        }

        return recipeResponseList;
    }

    @Transactional
    public List<RecipeResponseDTO> getRecipesByIngredients(List<String> ingredients){
        List<Recipe> recipeList = recipeRepo.findByIngredients(ingredients);
        List<RecipeResponseDTO> recipeResponseList= new ArrayList<>();
        for(Recipe recipe: recipeList){
            RecipeResponseDTO recipeResponse = recipeToRecipeResponseDTO(recipe);
            recipeResponseList.add(recipeResponse);
        }

        return recipeResponseList;
    }

    @Transactional
    public RecipeResponseDTO getRandomRecipe(){
        List<Recipe> recipes = recipeRepo.findAll();
        Random random = new Random();
        Recipe randomRecipe = recipes.get(random.nextInt(recipes.size()));
        RecipeResponseDTO recipeRespone = recipeToRecipeResponseDTO(randomRecipe);
        return recipeRespone;
    }

    @Transactional
    public void deleteRecipe(Long id) {
        try{
            recipeRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new RuntimeException("Recipe with id " + id + " not found");
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private RecipeResponseDTO recipeToRecipeResponseDTO(Recipe recipe){
        RecipeResponseDTO recipeResponse = new RecipeResponseDTO();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setName(recipe.getName());
        recipeResponse.setPhoto(recipe.getPhoto());
        recipeResponse.setDescription(recipe.getDescription());
        List<String> responseList = new ArrayList<>();
        for(RecipeIngredient recipeIngredient: recipe.getRecipeIngredientList()){
            Ingredient ingredient = recipeIngredient.getIngredient();
            String ingredientQuantity = ingredient.getName() + " - " + recipeIngredient.getQuantity();
            responseList.add(ingredientQuantity);
        }
        recipeResponse.setIngredientsList(responseList);
        return recipeResponse;
    }
}
