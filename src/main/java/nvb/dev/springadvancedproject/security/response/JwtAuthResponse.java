package nvb.dev.springadvancedproject.security.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthResponse {

    private String token;
    private String refreshToken;

}
