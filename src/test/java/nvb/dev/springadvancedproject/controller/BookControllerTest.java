package nvb.dev.springadvancedproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.springadvancedproject.dto.BookDto;
import nvb.dev.springadvancedproject.mapper.BookMapper;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.security.JwtService;
import nvb.dev.springadvancedproject.security.impl.UserDetailsServiceImpl;
import nvb.dev.springadvancedproject.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static nvb.dev.springadvancedproject.constant.Constant.BEARER;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @Mock
    BookMapper bookMapper;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    BookService bookService;

    private String generateToken() {
        UserDetails user = User.builder()
                .username("anyString")
                .password("anyString")
                .roles("ADMIN")
                .build();
        return jwtService.generateToken(user);
    }

    @Test
    void testThatSaveBookReturnsHttpStatusCode200Ok() throws Exception {
        when(bookService.saveBook(anyLong(), any(BookEntity.class))).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(anyValidBookDto());

        mockMvc.perform(post("/api/book/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void testThatSaveBookReturnsHttpStatusCode400BadRequestWhenTitleIsBlank() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setTitle("");

        BookDto bookDto = anyValidBookDto();
        bookDto.setTitle("");

        when(bookService.saveBook(anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(post("/api/book/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveBookReturnsHttpStatusCode400BadRequestWhenTitleIsNull() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setTitle(null);

        BookDto bookDto = anyValidBookDto();
        bookDto.setTitle(null);

        when(bookService.saveBook(anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(post("/api/book/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveBookReturnsHttpStatusCode400BadRequestWhenIsbnIsBlank() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setIsbn("");

        BookDto bookDto = anyValidBookDto();
        bookDto.setIsbn("");

        when(bookService.saveBook(anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(post("/api/book/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveBookReturnsHttpStatusCode400BadRequestWhenIsbnIsNull() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setIsbn(null);

        BookDto bookDto = anyValidBookDto();
        bookDto.setIsbn(null);

        when(bookService.saveBook(anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(post("/api/book/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetBookByIdReturnsHttpStatusCode200Ok() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetBookByIdReturnsHttpStatusCode404NotFound() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(Optional.empty());
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testThatGetAllBooksReturnsHttpStatusCode200Ok() throws Exception {
        when(bookService.getAllBooks(anyInt(), anyInt())).thenReturn(Collections.singletonList(anyValidBookEntity()));
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/book/all/simple")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetAllBooksWithSortPropertyReturnsHttpStatusCode200Ok() throws Exception {
        when(bookService.getAllBooks(anyInt(), anyInt(), anyString())).thenReturn(Collections.singletonList(anyValidBookEntity()));
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/book/all/bySort")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortProperty", "id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetBooksByAuthorIdReturnsHttpStatusCode200Ok() throws Exception {
        when(bookService.getBooksByAuthorId(anyLong())).thenReturn(List.of(anyValidBookEntity()));
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/book/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatUpdateBookReturnsHttpStatusCode200Ok() throws Exception {
        when(bookService.updateBook(anyLong(), anyLong(), any(BookEntity.class))).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(anyValidBookDto());

        mockMvc.perform(put("/api/book/1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatUpdateBookReturnsHttpStatusCode400BadRequestWhenTitleIsBlank() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setTitle("");

        BookDto bookDto = anyValidBookDto();
        bookDto.setTitle("");

        when(bookService.updateBook(anyLong(), anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(put("/api/book/1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateBookReturnsHttpStatusCode400BadRequestWhenTitleIsNull() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setTitle(null);

        BookDto bookDto = anyValidBookDto();
        bookDto.setTitle(null);

        when(bookService.updateBook(anyLong(), anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(put("/api/book/1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateBookReturnsHttpStatusCode400BadRequestWhenIsbnIsBlank() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setIsbn("");

        BookDto bookDto = anyValidBookDto();
        bookDto.setIsbn("");

        when(bookService.updateBook(anyLong(), anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(put("/api/book/1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatUpdateBookReturnsHttpStatusCode400BadRequestWhenIsbnIsNull() throws Exception {
        BookEntity bookEntity = anyValidBookEntity();
        bookEntity.setIsbn(null);

        BookDto bookDto = anyValidBookDto();
        bookDto.setIsbn(null);

        when(bookService.updateBook(anyLong(), anyLong(), any(BookEntity.class))).thenReturn(bookEntity);
        when(bookMapper.toBookEntity(bookDto)).thenReturn(bookEntity);
        when(bookMapper.toBookDto(bookEntity)).thenReturn(bookDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(put("/api/book/1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatPartialUpdateReturnsHttpStatusCode200Ok() throws Exception {
        when(bookService.partialUpdate(anyLong(), anyLong(), any(Map.class))).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookEntity(anyValidBookDto())).thenReturn(anyValidBookEntity());
        when(bookMapper.toBookDto(anyValidBookEntity())).thenReturn(anyValidBookDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String bookJson = objectMapper.writeValueAsString(anyValidBookDto());

        mockMvc.perform(patch("/api/book/1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                        .content(bookJson)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatDeleteBookByAuthorIdReturnsHttpStatusCode204NoContent() throws Exception {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/book/1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNoContent());
    }

}