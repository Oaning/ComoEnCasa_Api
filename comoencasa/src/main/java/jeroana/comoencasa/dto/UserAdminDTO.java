package jeroana.comoencasa.dto;

import jakarta.validation.constraints.Email;
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
public class UserAdminDTO {
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Email
    @Size(min = 5, max = 25)
    private String email;

    @NotBlank
    @Size(min = 5, max = 25)
    private String password;

    @NotBlank
    @Size(min = 3, max = 25)
    private String name;
}