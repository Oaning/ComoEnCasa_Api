package jeroana.comoencasa.controller;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeroana.comoencasa.dto.UserDTO;
import jeroana.comoencasa.service.UserService;
import jeroana.comoencasa.model.User;
import jeroana.comoencasa.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    private UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> getUserLogin(@RequestHeader String email, @RequestHeader String password){
        Predicate<User> login = p -> p.getEmail().equals(email) && p.getPassword().equals(password);
        User user = userRepo.findAll().stream().filter(login).findFirst().orElse(null);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @GetMapping("/all")
    public List<UserDTO> getAll(){
        return userService.getAll();
    }

    @PostMapping("/new")
    public UserDTO newUser(@RequestBody UserDTO user){
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @PutMapping("/update")
    public UserDTO updateUser(@RequestBody UserDTO user){
        return userService.saveUser(user);
    }
}
