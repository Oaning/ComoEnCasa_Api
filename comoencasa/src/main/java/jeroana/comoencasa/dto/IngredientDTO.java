package jeroana.comoencasa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IngredientDTO {
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 3, max = 25)
    private String name;

    private int fromMonth;

    private int toMonth;

    @NotBlank
    @Size(min = 3, max = 25)
    private String type;

    public IngredientDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
