package nvb.dev.springadvancedproject.security;

import nvb.dev.springadvancedproject.security.request.AuthRequest;
import nvb.dev.springadvancedproject.security.request.RefreshTokenRequest;
import nvb.dev.springadvancedproject.security.response.JwtAuthResponse;

public interface AuthService {

    JwtAuthResponse authenticate(AuthRequest authRequest);

    JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
