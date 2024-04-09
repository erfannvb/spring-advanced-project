package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.dto.request.ChangePasswordRequest;
import nvb.dev.springadvancedproject.model.UserEntity;

public interface UserService {

    UserEntity saveUser(UserEntity userEntity);

    void changePassword(String userId, ChangePasswordRequest request);

}
