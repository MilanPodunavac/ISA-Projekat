package code.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class OwnerCommentary {
    @Column
    private String commentary;
    @Column
    private boolean sanctionSuggested;
    @Column
    private boolean clientCame;
    @Column
    private boolean penaltyGiven;
}
