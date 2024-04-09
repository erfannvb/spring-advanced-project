package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.request.ChangePasswordRequest;
import nvb.dev.springadvancedproject.exception.user.PasswordsNotMatchException;
import nvb.dev.springadvancedproject.exception.user.UserNotFoundException;
import nvb.dev.springadvancedproject.exception.user.UsernameExistsException;
import nvb.dev.springadvancedproject.exception.user.WrongPasswordException;
import nvb.dev.springadvancedproject.model.UserEntity;
import nvb.dev.springadvancedproject.repository.UserRepository;
import nvb.dev.springadvancedproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        if (usernameExists(userEntity.getUsername())) throw new UsernameExistsException(userEntity.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public void changePassword(String userId, ChangePasswordRequest request) {
        userRepository.findById(userId)
                .ifPresentOrElse(
                        userEntity -> {
                            validateOldPassword(request.getOldPassword(), userEntity.getPassword());
                            validateNewPasswords(request.getNewPassword(), request.getConfirmPassword());
                            userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
                            userRepository.save(userEntity);
                        },
                        () -> {
                            throw new UserNotFoundException();
                        }
                );
    }

    private void validateOldPassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new WrongPasswordException();
        }
    }

    private void validateNewPasswords(String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordsNotMatchException();
        }
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

}
