package nvb.dev.springadvancedproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.springadvancedproject.mapper.LoanMapper;
import nvb.dev.springadvancedproject.model.LoanEntity;
import nvb.dev.springadvancedproject.security.JwtService;
import nvb.dev.springadvancedproject.security.impl.UserDetailsServiceImpl;
import nvb.dev.springadvancedproject.service.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static nvb.dev.springadvancedproject.constant.Constant.BEARER;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class LoanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    LoanMapper loanMapper;

    @Autowired
    JwtService jwtService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    LoanService loanService;

    private String generateToken() {
        UserDetails user = User.builder()
                .username("anyString")
                .password("anyString")
                .roles("ADMIN")
                .build();
        return jwtService.generateToken(user);
    }

    @Test
    void testThatLoanBookToMemberReturnHttpStatusCode200Ok() throws Exception {
        when(loanService.loanBookToMember(anyLong(), anyLong(), any(LoanEntity.class))).thenReturn(anyValidLoanEntity());
        when(loanMapper.toLoanEntity(anyValidLoanDto())).thenReturn(anyValidLoanEntity());
        when(loanMapper.toLoanDto(anyValidLoanEntity())).thenReturn(anyValidLoanDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String loanJson = objectMapper.writeValueAsString(anyValidLoanDto());

        mockMvc.perform(post("/api/loan/submit/member/1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(loanJson)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void testThatDeleteLoanReturnsHttpStatus204NoContent() throws Exception {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/loan/delete/member/1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNoContent());
    }

}