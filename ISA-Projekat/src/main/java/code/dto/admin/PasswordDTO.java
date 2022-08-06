package code.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {
    @NotNull(message = "Id is required")
    @Min(value = 1, message = "Id must be at least 1")
    private Integer id;
    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password too short")
    private String password;
}
