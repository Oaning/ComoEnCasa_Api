package jeroana.comoencasa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserRecipeDTO {
    @NotBlank
    private Long user_id;

    @NotBlank
    private Long recipe_id;
}
