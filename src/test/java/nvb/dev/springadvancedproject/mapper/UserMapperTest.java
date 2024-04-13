package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.UserDto;
import nvb.dev.springadvancedproject.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserMapperImpl.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void testUserInstanceIsNotNull() {
        UserMapper instance = UserMapper.INSTANCE;
        assertNotNull(instance);
    }

    @Test
    void toUserDto() {
        UserDto userDto = userMapper.toUserDto(anyValidUserEntity());
        assertEquals(anyValidUserEntity().getId(), userDto.getId());
        assertEquals(anyValidUserEntity().getFirstName(), userDto.getFirstName());
        assertEquals(anyValidUserEntity().getLastName(), userDto.getLastName());
        assertEquals(anyValidUserEntity().getUsername(), userDto.getUsername());
        assertEquals(anyValidUserEntity().getEmail(), userDto.getEmail());
        assertEquals(anyValidUserEntity().getPassword(), userDto.getPassword());
        assertEquals(anyValidUserEntity().getUserRole(), userDto.getUserRole());
    }

    @Test
    void toUserDto_Null_Fields() {
        UserDto userDto = userMapper.toUserDto(anyInvalidUserEntity());
        assertNull(userDto.getId());
        assertNull(userDto.getFirstName());
        assertNull(userDto.getLastName());
        assertNull(userDto.getUsername());
        assertNull(userDto.getEmail());
        assertNull(userDto.getPassword());
        assertNull(userDto.getUserRole());
    }

    @Test
    void toUserDto_Null() {
        UserDto userDto = userMapper.toUserDto(null);
        assertNull(userDto);
    }

    @Test
    void toUserEntity() {
        UserEntity userEntity = userMapper.toUserEntity(anyValidUserDto());
        assertEquals(anyValidUserDto().getId(), userEntity.getId());
        assertEquals(anyValidUserDto().getFirstName(), userEntity.getFirstName());
        assertEquals(anyValidUserDto().getLastName(), userEntity.getLastName());
        assertEquals(anyValidUserDto().getUsername(), userEntity.getUsername());
        assertEquals(anyValidUserDto().getEmail(), userEntity.getEmail());
        assertEquals(anyValidUserDto().getPassword(), userEntity.getPassword());
        assertEquals(anyValidUserDto().getUserRole(), userEntity.getUserRole());
    }

    @Test
    void toUserEntity_Null_Fields() {
        UserEntity userEntity = userMapper.toUserEntity(anyInvalidUserDto());
        assertNull(userEntity.getId());
        assertNull(userEntity.getFirstName());
        assertNull(userEntity.getLastName());
        assertNull(userEntity.getUsername());
        assertNull(userEntity.getEmail());
        assertNull(userEntity.getPassword());
        assertNull(userEntity.getUserRole());
    }

    @Test
    void toUserEntity_Null() {
        UserEntity userEntity = userMapper.toUserEntity(null);
        assertNull(userEntity);
    }
}