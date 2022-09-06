package code.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicInfo {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
