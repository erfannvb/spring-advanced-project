package nvb.dev.springadvancedproject.dto.request;

import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.UserRole;

@Data
@Builder
public class UserRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private UserRole userRole;

}
