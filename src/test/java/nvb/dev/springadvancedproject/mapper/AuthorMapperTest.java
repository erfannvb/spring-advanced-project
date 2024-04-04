package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.AuthorDto;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.springadvancedproject.MotherObject.anyValidAuthorDto;
import static nvb.dev.springadvancedproject.MotherObject.anyValidAuthorEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AuthorMapperImpl.class})
class AuthorMapperTest {

    @Autowired
    AuthorMapper authorMapper;

    @Test
    void toAuthorDto() {
        AuthorDto authorDto = authorMapper.toAuthorDto(anyValidAuthorEntity());
        assertEquals(anyValidAuthorEntity().getId(), authorDto.getId());
        assertEquals(anyValidAuthorEntity().getFirstName(), authorDto.getFirstName());
        assertEquals(anyValidAuthorEntity().getLastName(), authorDto.getLastName());
        assertEquals(anyValidAuthorEntity().getAge(), authorDto.getAge());
        assertEquals(anyValidAuthorEntity().getBooks(), authorDto.getBooks());
    }

    @Test
    void toAuthorDto_Null_Fields() {
        AuthorEntity authorEntity = anyValidAuthorEntity();
        authorEntity.setId(null);
        authorEntity.setFirstName(null);
        authorEntity.setLastName(null);
        authorEntity.setAge(null);
        authorEntity.setBooks(null);

        AuthorDto authorDto = authorMapper.toAuthorDto(authorEntity);

        assertNull(authorDto.getId());
        assertNull(authorDto.getFirstName());
        assertNull(authorDto.getLastName());
        assertNull(authorDto.getAge());
        assertNull(authorDto.getBooks());
    }

    @Test
    void toAuthorDto_Null() {
        AuthorDto authorDto = authorMapper.toAuthorDto(null);
        assertNull(authorDto);
    }

    @Test
    void toAuthorEntity() {
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(anyValidAuthorDto());
        assertEquals(anyValidAuthorDto().getId(), authorEntity.getId());
        assertEquals(anyValidAuthorDto().getFirstName(), authorEntity.getFirstName());
        assertEquals(anyValidAuthorDto().getLastName(), authorEntity.getLastName());
        assertEquals(anyValidAuthorDto().getAge(), authorEntity.getAge());
        assertEquals(anyValidAuthorDto().getBooks(), authorEntity.getBooks());
    }

    @Test
    void toAuthorEntity_Null_Fields() {
        AuthorDto authorDto = anyValidAuthorDto();
        authorDto.setId(null);
        authorDto.setFirstName(null);
        authorDto.setLastName(null);
        authorDto.setAge(null);
        authorDto.setBooks(null);

        AuthorEntity authorEntity = authorMapper.toAuthorEntity(authorDto);

        assertNull(authorEntity.getId());
        assertNull(authorEntity.getFirstName());
        assertNull(authorEntity.getLastName());
        assertNull(authorEntity.getAge());
    }

    @Test
    void toAuthorEntity_Null() {
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(null);
        assertNull(authorEntity);
    }
}