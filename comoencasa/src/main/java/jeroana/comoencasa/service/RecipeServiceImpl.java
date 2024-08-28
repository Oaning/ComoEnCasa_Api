package jeroana.comoencasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        if((recipeDto.getId() == null || recipeDto.getId() == 0)&& recipeExists == null){
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
        Ingredient ingredient = ingredientRepo.findByName(recipeIngredientDto.getIngredient());
        if(ingredient != null){
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setQuantity(recipeIngredientDto.getQuantity());
            
            recipeIngredientRepo.save(recipeIngredient);
        }
    }
    
    @Transactional
    public void newAdminRecipe(@Valid RecipeDTO recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        recipe = recipeRepo.save(recipe);

        List<RecipeIngredient> recipeList = new ArrayList<>();
        for(RecipeIngredientDTO ri : recipeDto.getIngredientsList()){
            Ingredient ingredient = ingredientRepo.findByName(ri.getIngredient());
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            
            recipeIngredient = new RecipeIngredient();
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setQuantity(ri.getQuantity());
            recipeIngredientRepo.save(recipeIngredient);        
            
            recipeList.add(recipeIngredient);
        }
        recipe.setRecipeIngredientList(recipeList);
        recipeRepo.save(recipe);
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
    public RecipeResponseDTO getRecipeByName(String name) {
        RecipeResponseDTO recipeResponse = null;
        Recipe recipe = recipeRepo.findByName(name);
        if(recipe != null){
            recipeResponse = recipeToRecipeResponseDTO(recipe);
        }
        return recipeResponse;
    }
    
    @Transactional
    public void updateRecipe(RecipeDTO recipeDto){
        Recipe recipe = recipeRepo.findById(recipeDto.getId()).orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setPhoto(recipeDto.getPhoto());
        List<RecipeIngredient> recipeList = new ArrayList<>();
        for(RecipeIngredientDTO ri : recipeDto.getIngredientsList()){
            Ingredient ingredient = ingredientRepo.findByName(ri.getIngredient());
            RecipeIngredient recipeIngredient = recipeIngredientRepo.findByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId()).orElse(null);
            if(Objects.nonNull(recipeIngredient)){
                recipeIngredient.setQuantity(ri.getQuantity());
                recipeIngredientRepo.save(recipeIngredient);
            }
            else{
                recipeIngredient = new RecipeIngredient();
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setQuantity(ri.getQuantity());
                recipeIngredientRepo.save(recipeIngredient);        
            }
            recipeList.add(recipeIngredient);
        }
        recipe.setRecipeIngredientList(recipeList);
        recipe = recipeRepo.save(recipe);
        
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
    public List<RecipeDTO> getAllRecipes(){
        List<Recipe> recipeList = recipeRepo.findAll();
        List<RecipeDTO> recipeDTOs = new ArrayList<>();
        for(Recipe recipe: recipeList){
            RecipeDTO recipeDto = modelMapper.map(recipe, RecipeDTO.class);
            List<RecipeIngredientDTO> ingredientsList = new ArrayList<>();
            for(RecipeIngredient ri: recipe.getRecipeIngredientList()){
                RecipeIngredientDTO riDto = new RecipeIngredientDTO();
                riDto.setIngredient(ri.getIngredient().getName());
                riDto.setQuantity(ri.getQuantity());
                ingredientsList.add(riDto);
            }
            recipeDto.setIngredientsList(ingredientsList);
            recipeDTOs.add(recipeDto);
        }
        return recipeDTOs;
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
            recipeIngredientRepo.deleteByRecipeId(id);
            recipeRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new RuntimeException("Recipe with id " + id + " not found");
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public static RecipeResponseDTO recipeToRecipeResponseDTO(Recipe recipe){
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
