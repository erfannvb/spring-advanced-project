package nvb.dev.springadvancedproject.mapper;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.UserDto;
import nvb.dev.springadvancedproject.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto mapToUserDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public UserEntity mapToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

}
