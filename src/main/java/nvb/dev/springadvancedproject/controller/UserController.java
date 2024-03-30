package nvb.dev.springadvancedproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.UserDto;
import nvb.dev.springadvancedproject.mapper.UserMapper;
import nvb.dev.springadvancedproject.model.UserEntity;
import nvb.dev.springadvancedproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(path = "/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        UserEntity userEntity = userMapper.toUserEntity(userDto);
        UserEntity savedUser = userService.saveUser(userEntity);
        return new ResponseEntity<>(userMapper.toUserDto(savedUser), HttpStatus.CREATED);
    }

}
