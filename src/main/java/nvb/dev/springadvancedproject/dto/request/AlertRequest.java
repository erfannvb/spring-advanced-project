package nvb.dev.springadvancedproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AlertRequest implements Serializable {

    @NotBlank(message = "message cannot be blank.")
    private String message;

}
