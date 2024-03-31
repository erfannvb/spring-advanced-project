package nvb.dev.springadvancedproject.security.request;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;

}
