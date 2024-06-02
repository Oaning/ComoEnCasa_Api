package jeroana.comoencasa.service;

import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jeroana.comoencasa.dto.UserDTO;

@Service
public interface UserService {
    public UserDTO saveUser(@Valid UserDTO user);

    public UserDTO login(String email, String password);

    public UserDTO getUser(Long id);

    public List<UserDTO> getAll();

    public void deleteUser(Long id);
}
