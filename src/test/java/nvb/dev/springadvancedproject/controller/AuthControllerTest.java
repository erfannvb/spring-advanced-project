package nvb.dev.springadvancedproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.springadvancedproject.security.AuthService;
import nvb.dev.springadvancedproject.security.request.AuthRequest;
import nvb.dev.springadvancedproject.security.request.RefreshTokenRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static nvb.dev.springadvancedproject.MotherObject.anyValidJwtAuthResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

    @Test
    void testThatAuthenticateReturnsHttpStatus200Ok() throws Exception {
        when(authService.authenticate(any(AuthRequest.class))).thenReturn(anyValidJwtAuthResponse());

        String response = objectMapper.writeValueAsString(anyValidJwtAuthResponse());

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(response)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatRefreshTokenReturnsHttpStatus200Ok() throws Exception {
        when(authService.refreshToken(any(RefreshTokenRequest.class))).thenReturn(anyValidJwtAuthResponse());

        String response = objectMapper.writeValueAsString(anyValidJwtAuthResponse());

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(response)
                )
                .andExpect(status().isOk());
    }
}