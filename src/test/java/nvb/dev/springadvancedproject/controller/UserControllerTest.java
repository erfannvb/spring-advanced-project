package nvb.dev.springadvancedproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.springadvancedproject.dto.request.ChangePasswordRequest;
import nvb.dev.springadvancedproject.mapper.UserMapper;
import nvb.dev.springadvancedproject.model.UserEntity;
import nvb.dev.springadvancedproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static nvb.dev.springadvancedproject.MotherObject.anyValidUserDto;
import static nvb.dev.springadvancedproject.MotherObject.anyValidUserEntity;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    UserMapper userMapper;

    @MockBean
    UserService userService;

    @Test
    void TestThatSaveUserReturnsHttpStatus201Created() throws Exception {
        when(userService.saveUser(any(UserEntity.class))).thenReturn(anyValidUserEntity());
        when(userMapper.toUserEntity(anyValidUserDto())).thenReturn(anyValidUserEntity());
        when(userMapper.toUserDto(anyValidUserEntity())).thenReturn(anyValidUserDto());

        String userJson = objectMapper.writeValueAsString(anyValidUserDto());

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.firstName").value("dummy"))
                .andExpect(jsonPath("$.lastName").value("dummy"));
    }

    @Test
    void testThatChangePasswordReturnsHttpStatusOk() throws Exception {
        when(userService.saveUser(any(UserEntity.class))).thenReturn(anyValidUserEntity());

        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .oldPassword("oldPassword")
                .newPassword("newPassword")
                .confirmPassword("newPassword")
                .build();

        String password = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/api/user/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(password)
                )
                .andExpect(status().isOk());

    }

}