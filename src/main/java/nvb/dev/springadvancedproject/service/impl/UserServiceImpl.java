package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.exception.user.UsernameExistsException;
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

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

}
