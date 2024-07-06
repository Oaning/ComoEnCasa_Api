package jeroana.comoencasa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jeroana.comoencasa.dto.IngredientDTO;
import jeroana.comoencasa.model.Ingredient;
import jeroana.comoencasa.repository.IngredientRepository;

@Service
@Validated
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientRepository ingredientRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    @Transactional
    public IngredientDTO newIngredient(@Valid IngredientDTO ingredientDto) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        if(ingredientDto.getId() == null){
            ingredient = ingredientRepo.save(ingredient);
        }
        return modelMapper.map(ingredient, IngredientDTO.class);
    }

    @Transactional
    public IngredientDTO getIngredient(Long id) {
        IngredientDTO ingredientDto = null;
        Ingredient ingredient = ingredientRepo.findById(id).orElse(null);
        if(ingredient != null){
            ingredientDto = modelMapper.map(ingredient, IngredientDTO.class);
        }
        
        return ingredientDto;
    }

    public IngredientDTO updateIngredient(IngredientDTO ingredientDto) {
        Ingredient ingredient = ingredientRepo.findById(ingredientDto.getId()).orElseThrow(() -> new RuntimeException("Ingredient not found"));
        ingredient.setName(ingredientDto.getName());
        ingredient.setFromMonth(ingredientDto.getFromMonth());
        ingredient.setToMonth(ingredientDto.getToMonth());
        ingredient.setType(ingredientDto.getType());
        ingredient =  ingredientRepo.save(ingredient);
        return modelMapper.map(ingredient, IngredientDTO.class);
    }

    @Transactional
    public List<IngredientDTO> getAll() {
        List<Ingredient> listIngredient = ingredientRepo.findAll();

        return listIngredient.stream().map(ingredient -> modelMapper.map(ingredient, IngredientDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void deleteIngredient(Long id) {
        try{
            ingredientRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new RuntimeException("Ingredient with id " + id + " not found");
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
