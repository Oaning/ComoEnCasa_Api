package jeroana.comoencasa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jeroana.comoencasa.model.Recipe;
import jeroana.comoencasa.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void post_saveUser_WithValidParameters_ShouldReturn200(){
        String email = "paco@email.com";
        String pass = "paco1234";
        String name = "paco";

        Recipe tartaQueso = new Recipe();
        tartaQueso.setName("Tarta de queso");
        recipeRepository.save(tartaQueso);

        User paco = new User();
        paco.setEmail(email);
        paco.setPassword(pass);
        paco.setName(name);

        userRepository.save(paco);

        userRepository.flush();

        assertEquals(1, userRepository.findAll().size());

        User pacoFind = userRepository.findByEmail(email);
        assertEquals(tartaQueso, pacoFind.getRecipesList().get(0));
    }
}
