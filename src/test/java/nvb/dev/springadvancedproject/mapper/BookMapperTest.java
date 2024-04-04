package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.BookDto;
import nvb.dev.springadvancedproject.model.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BookMapperImpl.class})
class BookMapperTest {

    @Autowired
    BookMapper bookMapper;

    @Test
    void toBookDto() {
        BookDto bookDto = bookMapper.toBookDto(anyValidBookEntity());
        assertEquals(anyValidBookEntity().getId(), bookDto.getId());
        assertEquals(anyValidBookEntity().getTitle(), bookDto.getTitle());
        assertEquals(anyValidBookEntity().getIsbn(), bookDto.getIsbn());
        assertEquals(anyValidBookEntity().getPages(), bookDto.getPages());
        assertEquals(anyValidBookEntity().getRating(), bookDto.getRating());
        assertEquals(anyValidBookEntity().getGenres(), bookDto.getGenres());
        assertEquals(anyValidBookEntity().getPublishedYear(), bookDto.getPublishedYear());
        assertEquals(anyValidBookEntity().getAuthor(), bookDto.getAuthor());
        assertEquals(anyValidBookEntity().getMember(), bookDto.getMember());
        assertEquals(anyValidBookEntity().getLoans(), bookDto.getLoans());
    }

    @Test
    void toBookDto_Null_Fields() {
        BookDto bookDto = bookMapper.toBookDto(anyInvalidBookEntity());
        assertNull(bookDto.getId());
        assertNull(bookDto.getTitle());
        assertNull(bookDto.getIsbn());
        assertNull(bookDto.getGenres());
        assertNull(bookDto.getPublishedYear());
        assertNull(bookDto.getAuthor());
        assertNull(bookDto.getMember());
        assertNull(bookDto.getLoans());
    }

    @Test
    void toBookDto_Null() {
        BookDto bookDto = bookMapper.toBookDto(null);
        assertNull(bookDto);
    }

    @Test
    void toBookEntity() {
        BookEntity bookEntity = bookMapper.toBookEntity(anyValidBookDto());
        assertEquals(anyValidBookDto().getId(), bookEntity.getId());
        assertEquals(anyValidBookDto().getTitle(), bookEntity.getTitle());
        assertEquals(anyValidBookDto().getIsbn(), bookEntity.getIsbn());
        assertEquals(anyValidBookDto().getPages(), bookEntity.getPages());
        assertEquals(anyValidBookDto().getRating(), bookEntity.getRating());
        assertEquals(anyValidBookDto().getGenres(), bookEntity.getGenres());
        assertEquals(anyValidBookDto().getPublishedYear(), bookEntity.getPublishedYear());
        assertEquals(anyValidBookDto().getAuthor(), bookEntity.getAuthor());
        assertEquals(anyValidBookDto().getMember(), bookEntity.getMember());
        assertEquals(anyValidBookDto().getLoans(), bookEntity.getLoans());
    }

    @Test
    void toBookEntity_Null_Fields() {
        BookEntity bookEntity = bookMapper.toBookEntity(anyInvalidBookDto());
        assertNull(bookEntity.getId());
        assertNull(bookEntity.getTitle());
        assertNull(bookEntity.getIsbn());
        assertNull(bookEntity.getGenres());
        assertNull(bookEntity.getPublishedYear());
        assertNull(bookEntity.getAuthor());
        assertNull(bookEntity.getMember());
    }

    @Test
    void toBookEntity_Null() {
        BookEntity bookEntity = bookMapper.toBookEntity(null);
        assertNull(bookEntity);
    }
}