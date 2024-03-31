package nvb.dev.springadvancedproject.security.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.exception.UserNotFoundException;
import nvb.dev.springadvancedproject.model.UserEntity;
import nvb.dev.springadvancedproject.repository.UserRepository;
import nvb.dev.springadvancedproject.security.AuthService;
import nvb.dev.springadvancedproject.security.JwtService;
import nvb.dev.springadvancedproject.security.request.AuthRequest;
import nvb.dev.springadvancedproject.security.request.RefreshTokenRequest;
import nvb.dev.springadvancedproject.security.response.JwtAuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public JwtAuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        UserEntity userEntity = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(UserNotFoundException::new);

        String token = jwtService.generateToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userEntity);

        return JwtAuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        if (!jwtService.isTokenValid(refreshTokenRequest.getToken(), userEntity)) return null;

        String token = jwtService.generateToken(userEntity);
        return JwtAuthResponse.builder()
                .token(token)
                .refreshToken(refreshTokenRequest.getToken())
                .build();
    }
}
