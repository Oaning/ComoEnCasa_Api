package jeroana.comoencasa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeroana.comoencasa.dto.LoginDTO;
import jeroana.comoencasa.dto.UserAdminDTO;
import jeroana.comoencasa.dto.UserDTO;
import jeroana.comoencasa.dto.UserRecipeDTO;
import jeroana.comoencasa.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public UserDTO getUserLogin(@RequestBody LoginDTO loginDto){
        return userService.login(loginDto.getEmail(), loginDto.getPassword());
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @GetMapping("/all")
    public List<UserAdminDTO> getAll(){
        return userService.getAll();
    }

    @PostMapping("/new")
    public UserDTO newUser(@RequestBody UserDTO user){
        return userService.newUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @PutMapping("/update")
    public UserDTO updateUser(@RequestBody UserDTO user){
        return userService.updateUser(user);
    }

    @PostMapping("/addUserRecipe")
    public void addRecipeToUser(@RequestBody UserRecipeDTO userRecipe){
        userService.addRecipeToUser(userRecipe);
    }

    @PostMapping("/deleteUserRecipe")
    public void deleteRecipeFromUser(@RequestBody UserRecipeDTO userRecipe){
        userService.removeRecipeFromUser(userRecipe);
    }
}
