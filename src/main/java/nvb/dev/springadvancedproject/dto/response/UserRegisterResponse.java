package nvb.dev.springadvancedproject.dto.response;

import lombok.Data;
import nvb.dev.springadvancedproject.model.UserRole;

@Data
public class UserRegisterResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private UserRole userRole;

}
