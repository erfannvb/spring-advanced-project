package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.request.ChangePasswordRequest;
import nvb.dev.springadvancedproject.exception.user.UsernameExistsException;
import nvb.dev.springadvancedproject.model.UserEntity;
import nvb.dev.springadvancedproject.repository.UserRepository;
import nvb.dev.springadvancedproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<UserEntity> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {

            UserEntity userEntity = foundUser.get();

            if (!passwordEncoder.matches(request.getOldPassword(), userEntity.getPassword())) {
                throw new IllegalArgumentException("Wrong password");
            }

            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                throw new IllegalArgumentException("Passwords do not match");
            }

            userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(userEntity);

        }
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

}
