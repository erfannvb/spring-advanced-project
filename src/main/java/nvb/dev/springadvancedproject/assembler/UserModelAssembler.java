package nvb.dev.springadvancedproject.assembler;

import nvb.dev.springadvancedproject.controller.UserController;
import nvb.dev.springadvancedproject.dto.UserDto;
import nvb.dev.springadvancedproject.mapper.UserMapper;
import nvb.dev.springadvancedproject.model.UserEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserEntity, EntityModel<UserDto>> {

    @Override
    @NonNull
    public EntityModel<UserDto> toModel(@NonNull UserEntity user) {
        UserDto userDto = UserMapper.INSTANCE.toUserDto(user);
        return EntityModel.of(userDto,
                linkTo(methodOn(UserController.class)
                        .changePassword(userDto.getId(), null))
                        .withRel("change-password"));
    }
}
