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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jeroana.comoencasa.dto.IngredientDTO;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.model.Recipe;
import jeroana.comoencasa.repository.IngredientRepository;
import jeroana.comoencasa.repository.RecipeRepository;

@Service
@Validated
public class RecipeServiceImpl implements RecipeService{
    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private IngredientRepository ingredientRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public RecipeDTO saveRecipe(@Valid RecipeDTO recipe) {
        Recipe recipeEntity = modelMapper.map(recipe, Recipe.class);

        if(recipe.getId() == null){
            List<Long> ids = new ArrayList<Long>();
            for(IngredientDTO ingredients: recipe.getIngredientsList()){
                ids.add(ingredients.getId());
            }
            recipeEntity.setIngredientsList(ingredientRepo.findByIngredients(ids));
        }
        else {
            recipeEntity = recipeRepo.getReferenceById(recipe.getId());
            recipeEntity = modelMapper.map(recipe, Recipe.class);
        }

        recipeEntity = recipeRepo.save(recipeEntity);
        return modelMapper.map(recipeEntity, RecipeDTO.class);
    }

    @Transactional
    public RecipeDTO getRecipe(Long id) {
        RecipeDTO recipeDto = null;

        try{
            Recipe recipeEntity = recipeRepo.getReferenceById(id);
            recipeEntity.getName();
            recipeDto = modelMapper.map(recipeEntity, RecipeDTO.class);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Recipe with id " + id + " not found");
        } catch (Exception e){
            throw new RuntimeException();
        }

        return recipeDto;
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
