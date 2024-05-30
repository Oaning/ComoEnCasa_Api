package jeroana.comoencasa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolationException;
import jeroana.comoencasa.dto.UserDTO;

@SpringBootTest
public class UserConstraintsValidationExceptionTest {
    @Autowired
    private UserServiceImpl userService;
    
    @Test
    public void constraintsValidationExceptionTest(){

        //prueba nombre nulo
        assertThrows(ConstraintViolationException.class, () -> {userService.saveUser(new UserDTO("prueba@mail.com", "12345", null));});

        //prueba nombre demasiado largo
        assertThrows(ConstraintViolationException.class, () -> {userService.saveUser(new UserDTO("prueba@mail.com", "12345", "prueba1prueba2prueba3prueba4"));});

        //prueba email formato incorrecto
        assertThrows(ConstraintViolationException.class, () -> {userService.saveUser(new UserDTO("prueba.mail.com", "12345", "prueba"));});

        //prueba contraseña corta
        assertThrows(ConstraintViolationException.class, () -> {userService.saveUser(new UserDTO("prueba@mail.com", "1234","prueba"));});
        
        //prueba todo nulo
        assertThrows(ConstraintViolationException.class, () -> {userService.saveUser(new UserDTO(null, null, null));});
    }

    @Test
    public void createUserOk(){
        UserDTO userDto = userService.saveUser(new UserDTO("paco@mail.com", "12345", "paco"));
        
        assertNotNull(userDto);

        assertEquals("paco", userDto.getName());

        assertNotNull(userDto.getId());
    }
}
