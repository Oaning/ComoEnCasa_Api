package jeroana.comoencasa.service;

import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.UserDTO;
import jeroana.comoencasa.dto.UserRecipeDTO;

@Service
public interface UserService {
    public UserDTO login(String email, String password);
    
    public UserDTO newUser(@Valid UserDTO user);

    public UserDTO updateUser(@Valid UserDTO user);

    public UserDTO getUser(Long id);

    public List<UserDTO> getAll();

    public void deleteUser(Long id);

    public void addRecipeToUser(UserRecipeDTO userRecipe);

    public void removeRecipeFromUser(UserRecipeDTO userRecipe);
}
