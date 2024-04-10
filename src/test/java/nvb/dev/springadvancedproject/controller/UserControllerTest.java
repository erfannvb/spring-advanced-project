package nvb.dev.springadvancedproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.springadvancedproject.dto.UserDto;
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
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenFirstNameIsBlank() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setFirstName("");

        UserDto userDto = anyValidUserDto();
        userDto.setFirstName("");

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenFirstNameIsNull() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setFirstName(null);

        UserDto userDto = anyValidUserDto();
        userDto.setFirstName(null);

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenLastNameIsBlank() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setLastName("");

        UserDto userDto = anyValidUserDto();
        userDto.setLastName("");

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenLastNameIsNull() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setLastName(null);

        UserDto userDto = anyValidUserDto();
        userDto.setLastName(null);

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenUsernameIsBlank() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setUsername("");

        UserDto userDto = anyValidUserDto();
        userDto.setUsername("");

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenUsernameIsNull() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setUsername(null);

        UserDto userDto = anyValidUserDto();
        userDto.setUsername(null);

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenEmailIsBlank() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setEmail("");

        UserDto userDto = anyValidUserDto();
        userDto.setEmail("");

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenEmailIsNull() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setEmail(null);

        UserDto userDto = anyValidUserDto();
        userDto.setEmail(null);

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenPasswordIsBlank() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setPassword("");

        UserDto userDto = anyValidUserDto();
        userDto.setPassword("");

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveUserReturnsHttpStatusCode400BadRequestWhenPasswordIsNull() throws Exception {
        UserEntity userEntity = anyValidUserEntity();
        userEntity.setPassword(null);

        UserDto userDto = anyValidUserDto();
        userDto.setPassword(null);

        when(userService.saveUser(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isBadRequest());
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