package nvb.dev.springadvancedproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.assembler.UserModelAssembler;
import nvb.dev.springadvancedproject.dto.UserDto;
import nvb.dev.springadvancedproject.dto.request.ChangePasswordRequest;
import nvb.dev.springadvancedproject.mapper.UserMapper;
import nvb.dev.springadvancedproject.model.UserEntity;
import nvb.dev.springadvancedproject.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserModelAssembler userModelAssembler;

    @PostMapping(path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UserDto>> saveUser(@RequestBody @Valid UserDto userDto) {
        UserEntity userEntity = userMapper.toUserEntity(userDto);
        UserEntity savedUser = userService.saveUser(userEntity);
        EntityModel<UserDto> model = userModelAssembler.toModel(savedUser);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/change-password/{userId}")
    public ResponseEntity<Void> changePassword(@PathVariable String userId, @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
