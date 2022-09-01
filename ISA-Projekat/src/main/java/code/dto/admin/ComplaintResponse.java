package code.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintResponse {
    @NotBlank(message = "Response to provider is required")
    private String responseToProvider;
    @NotBlank(message = "Response to client is required")
    private String responseToClient;
}
