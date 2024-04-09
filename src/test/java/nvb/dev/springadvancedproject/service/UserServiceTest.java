package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.dto.request.ChangePasswordRequest;
import nvb.dev.springadvancedproject.exception.user.PasswordsNotMatchException;
import nvb.dev.springadvancedproject.exception.user.UserNotFoundException;
import nvb.dev.springadvancedproject.exception.user.UsernameExistsException;
import nvb.dev.springadvancedproject.exception.user.WrongPasswordException;
import nvb.dev.springadvancedproject.model.UserEntity;
import nvb.dev.springadvancedproject.repository.UserRepository;
import nvb.dev.springadvancedproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testThatSaveUserCanSaveUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUserEntity());
        when(passwordEncoder.encode(anyString())).thenReturn(anyValidUserEntity().getPassword());

        UserEntity savedUser = userService.saveUser(anyValidUserEntity());

        assertEquals("dummy", savedUser.getFirstName());
        assertEquals("dummy", savedUser.getLastName());
        assertEquals("dummy", savedUser.getUsername());

        verify(userRepository, atLeastOnce()).save(any(UserEntity.class));
    }

    @Test
    void testThatSaveUserCannotSaveUserIfUsernameExists() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUserEntity());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(anyValidUserEntity()));
        when(passwordEncoder.encode(anyString())).thenReturn(anyValidUserEntity().getPassword());

        assertThrows(UsernameExistsException.class, () -> userService.saveUser(anyValidUserEntity()));
        verify(userRepository, atLeastOnce()).findByUsername(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testThatChangePasswordWorksCorrectly() {
        String oldPassword = "dummy";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";

        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .confirmPassword(confirmPassword)
                .build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(anyValidUserEntity()));
        when(passwordEncoder.matches(oldPassword, anyValidUserEntity().getPassword())).thenReturn(true);

        userService.changePassword("123", request);

        verify(userRepository, atLeastOnce()).findById(anyString());
    }

    @Test
    void testThatChangePasswordThrowsUserNotFoundException() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUserEntity());
        when(passwordEncoder.encode(anyString())).thenReturn(anyValidUserEntity().getPassword());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                userService.changePassword("123", anyInvalidChangePasswordRequest()));

        assertEquals("User not found.", exception.getMessage());

        verify(userRepository, atLeastOnce()).findById(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testThatChangePasswordThrowsWrongPasswordException() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(anyValidUserEntity()));
        when(userRepository.save(any(UserEntity.class))).thenReturn(anyValidUserEntity());
        when(passwordEncoder.encode(anyString())).thenReturn(anyValidUserEntity().getPassword());

        WrongPasswordException exception = assertThrows(WrongPasswordException.class, () ->
                userService.changePassword("123", anyInvalidChangePasswordRequest()));

        assertEquals("Old password is incorrect.", exception.getMessage());

        verify(userRepository, atLeastOnce()).findById(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testThatChangePasswordThrowsPasswordsNotMatchException() {
        String oldPassword = "dummy";
        String newPassword = "newPassword";
        String confirmPassword = "confirmPassword";

        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .confirmPassword(confirmPassword)
                .build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(anyValidUserEntity()));
        when(passwordEncoder.matches(oldPassword, anyValidUserEntity().getPassword())).thenReturn(true);

        assertThrows(PasswordsNotMatchException.class, () ->
                userService.changePassword("123", request));

        verify(userRepository, atLeastOnce()).findById(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}