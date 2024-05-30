package jeroana.comoencasa.service;

import java.util.List;
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
    public IngredientDTO saveIngredient(@Valid IngredientDTO ingredient) {
        Ingredient ingredientEntity = modelMapper.map(ingredient, Ingredient.class);
        ingredientEntity = ingredientRepo.save(ingredientEntity);
        return modelMapper.map(ingredientEntity, IngredientDTO.class);
    }

    @Transactional
    public IngredientDTO getIngredient(Long id) {
        IngredientDTO ingredientDto = null;

        try{
            Ingredient ingredientEntity = ingredientRepo.getReferenceById(id);
            ingredientEntity.getName();
            ingredientDto = modelMapper.map(ingredientEntity, IngredientDTO.class);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Ingredient with id " + id + " not found");
        } catch (Exception e){
            throw new RuntimeException();
        }

        return ingredientDto;
    }

    @Transactional
    public List<IngredientDTO> getAll() {
        List<Ingredient> listIngredientEntity = ingredientRepo.findAll();
        List<IngredientDTO> listIngredientDto = listIngredientEntity.stream().map(ingredient -> modelMapper.map(ingredient, IngredientDTO.class)).collect(Collectors.toList());
        return listIngredientDto ;
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
