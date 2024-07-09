package jeroana.comoencasa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.UserDTO;
import jeroana.comoencasa.dto.UserRecipeDTO;
import jeroana.comoencasa.model.Recipe;
import jeroana.comoencasa.model.User;
import jeroana.comoencasa.repository.RecipeRepository;
import jeroana.comoencasa.repository.UserRepository;

@Service
@Validated
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public UserDTO login(String email, String password){
        User user = userRepo.findByEmail(email);
        if(user != null && user.getPassword().equals(password)){
            return modelMapper.map(user, UserDTO.class);
        }
        throw new EntityNotFoundException("Invalid user or password");
    }

    @Transactional
    public UserDTO newUser(UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        if(userDto.getId() == null){
            user = userRepo.save(user);
        }

        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(UserDTO userDto) {
        User user = userRepo.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user = userRepo.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO getUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public List<UserDTO> getAll() {
        List<User> listUserEntity = userRepo.findAll();
        return listUserEntity.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public void addRecipeToUser(UserRecipeDTO userRecipe){
        Long userId = userRecipe.getUser_id();
        Long recipeId = userRecipe.getRecipe_id();

        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Recipe recipe = recipeRepo.findById(recipeId).orElse(null);
        if(recipe != null){
            user.getRecipesList().add(recipe);
            userRepo.save(user);
        }
    }

    @Transactional
    public void removeRecipeFromUser(UserRecipeDTO userRecipe){
        Long userId = userRecipe.getUser_id();
        Long recipeId = userRecipe.getRecipe_id();

        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Recipe recipe = recipeRepo.findById(recipeId).orElse(null);
        if(recipe != null){
            user.getRecipesList().remove(recipe);
            userRepo.save(user);
        }
    }
}
