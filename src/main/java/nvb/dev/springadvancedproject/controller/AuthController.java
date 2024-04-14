package nvb.dev.springadvancedproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth Controller", description = "Authenticating the Existing User")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Generate JWT Token", description = "Generates JWT Token for the User")
    @ApiResponse(responseCode = "200", description = "Successful Generation of JWT Token")
    @PostMapping(path = "/authenticate")
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.authenticate(authRequest), HttpStatus.OK);
    }

    @Operation(summary = "Generate JWT Refresh Token", description = "Generates JWT Refresh Token for the User")
    @ApiResponse(responseCode = "200", description = "Successful Generation of JWT Refresh Token")
    @PostMapping(path = "/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

}
