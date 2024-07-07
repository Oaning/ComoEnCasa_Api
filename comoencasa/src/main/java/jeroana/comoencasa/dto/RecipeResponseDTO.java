package jeroana.comoencasa.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecipeResponseDTO {
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String photo;

    private String description;

    private List<String> ingredientsList;
}
