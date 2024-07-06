package jeroana.comoencasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.RecipeIngredientDTO;
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
    public RecipeDTO newRecipe(@Valid RecipeDTO recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        if(recipeDto.getId() == null){
            recipe = recipeRepo.save(recipe);

            List<RecipeIngredientDTO> ingredientsList = recipeDto.getIngredientsList();
            for(RecipeIngredientDTO riDto : ingredientsList){
                /*Ingredient ingredient = ingredientRepo.findById(riDto.getIngredient_id()).orElseThrow(() -> new RuntimeException("Ingredient not found"));
    
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setQuantity(riDto.getQuantity());
                recipeIngredientRepo.save(recipeIngredient);
*/
                addIngredientToRecipe(recipe.getId(), riDto);
            }
        }

        return modelMapper.map(recipe, RecipeDTO.class);
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
    public RecipeDTO getRecipe(Long id) {
        RecipeDTO recipeDto = null;
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if(recipe != null){
            recipeDto = modelMapper.map(recipe, RecipeDTO.class);
        }

        return recipeDto;
    }
    
    @Transactional
    public RecipeDTO updateRecipe(RecipeDTO recipeDto){
        Recipe recipe = recipeRepo.findById(recipeDto.getId()).orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setPhoto(recipeDto.getPhoto());

        List<RecipeIngredient> recipeList =new ArrayList<>();
        for(RecipeIngredientDTO ri : recipeDto.getIngredientsList()){
            RecipeIngredient recipeIngredient = modelMapper.map(ri, RecipeIngredient.class);
            recipeList.add(recipeIngredient);
        }
        recipe.setRecipeIngredientList(recipeList);
        recipe = recipeRepo.save(recipe);
        
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Transactional
    public List<RecipeDTO> getAll() {
        List<Recipe> listRecipeEntity = recipeRepo.findAll();
        return listRecipeEntity
            .stream()
            .map(recipe -> modelMapper.map(recipe, RecipeDTO.class))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<RecipeDTO> getRecipesByIngredients(List<String> ingredients){
        List<Recipe> recipes = recipeRepo.findByIngredients(ingredients);
        return recipes
            .stream()
            .map(recipe -> modelMapper.map(recipe, RecipeDTO.class))
            .collect(Collectors.toList());
    }

    @Transactional
    public RecipeDTO getRandomRecipe(){
        List<Recipe> recipes = recipeRepo.findAll();
        Random random = new Random();
        Recipe randomRecipe = recipes.get(random.nextInt(recipes.size()));
        return modelMapper.map(randomRecipe, RecipeDTO.class);
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

}
