package code.dto.user;

import code.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeletionRequestDtoGet {
    private Integer id;
    private String text;
    private User user;
}
