package code.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDto {
    @Length(min = 3, message = "Enter valid password")
    private String newPassword;
    @Length(min = 3, message = "Enter valid password")
    private String repeatedNewPassword;
}
