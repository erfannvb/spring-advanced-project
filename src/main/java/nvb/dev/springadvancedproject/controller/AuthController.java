package nvb.dev.springadvancedproject.controller;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.security.AuthService;
import nvb.dev.springadvancedproject.security.request.AuthRequest;
import nvb.dev.springadvancedproject.security.request.RefreshTokenRequest;
import nvb.dev.springadvancedproject.security.response.JwtAuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.authenticate(authRequest), HttpStatus.OK);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

}
