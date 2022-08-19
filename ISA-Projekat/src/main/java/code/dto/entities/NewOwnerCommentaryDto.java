package code.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewOwnerCommentaryDto {
    private String commentary;
    private boolean sanctionSuggested;
    private boolean clientCame;
}
