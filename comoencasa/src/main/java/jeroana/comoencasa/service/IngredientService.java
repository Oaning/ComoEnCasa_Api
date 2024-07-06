package jeroana.comoencasa.service;

import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jeroana.comoencasa.dto.IngredientDTO;

@Service
public interface IngredientService {
    public IngredientDTO newIngredient(@Valid IngredientDTO ingredient);

    public IngredientDTO getIngredient(Long id);

    public List<IngredientDTO> getAll();

    public void deleteIngredient(Long id);

    public IngredientDTO updateIngredient(@Valid IngredientDTO ingredient);
}
