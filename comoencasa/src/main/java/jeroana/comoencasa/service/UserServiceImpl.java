package jeroana.comoencasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jeroana.comoencasa.dto.RecipeDTO;
import jeroana.comoencasa.dto.UserDTO;
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
    public UserDTO saveUser(UserDTO user) {
        User userEntity = modelMapper.map(user, User.class);

        if(user.getId() == null){
            List<Long> ids = new ArrayList<Long>();
            if(!user.getRecipesList().isEmpty()){
                for(RecipeDTO recipes: user.getRecipesList()){
                    ids.add(recipes.getId());
                }
                userEntity.setRecipesList(recipeRepo.findByRecipes(ids));
            }
        }
        else{
            userEntity = userRepo.getReferenceById(user.getId());
            userEntity = modelMapper.map(user, User.class);
        }

        userEntity = userRepo.save(userEntity);
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Transactional
    public UserDTO getUser(Long id) {
        UserDTO userDto = null;

        try{
            User userEntity = userRepo.getReferenceById(id);
            userEntity.getEmail();
            userDto = modelMapper.map(userEntity, UserDTO.class);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("User with id " + id + " not found");
        } catch (Exception e){
            throw new RuntimeException();
        }

        return userDto;
    }

    @Transactional
    public List<UserDTO> getAll() {
        List<User> listUserEntity = userRepo.findAll();
        List<UserDTO> listUserDto = listUserEntity.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        return listUserDto ;
    }

    @Transactional
    public void deleteUser(Long id) {
        try{
            userRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new RuntimeException("User with id " + id + " not found");
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

}
