package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.UserDto;
import nvb.dev.springadvancedproject.model.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.username", target = "username")
    @Mapping(source = "userEntity.email", target = "email")
    @Mapping(source = "userEntity.password", target = "password")
    @Mapping(source = "userEntity.userRole", target = "userRole")
    UserDto toUserDto(UserEntity userEntity);

    @Mapping(source = "userDto.id", target = "id")
    @Mapping(source = "userDto.firstName", target = "firstName")
    @Mapping(source = "userDto.lastName", target = "lastName")
    @Mapping(source = "userDto.username", target = "username")
    @Mapping(source = "userDto.email", target = "email")
    @Mapping(source = "userDto.password", target = "password")
    @Mapping(source = "userDto.userRole", target = "userRole")
    UserEntity toUserEntity(UserDto userDto);

}
