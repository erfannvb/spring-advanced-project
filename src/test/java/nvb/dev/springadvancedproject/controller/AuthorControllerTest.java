package nvb.dev.springadvancedproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nvb.dev.springadvancedproject.dto.AuthorDto;
import nvb.dev.springadvancedproject.mapper.AuthorMapper;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.security.JwtService;
import nvb.dev.springadvancedproject.security.impl.UserDetailsServiceImpl;
import nvb.dev.springadvancedproject.service.AuthorService;
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

import java.util.*;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static nvb.dev.springadvancedproject.constant.Constant.BEARER;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @Mock
    AuthorMapper authorMapper;

    @MockBean
    AuthorService authorService;

    private String generateToken() {
        UserDetails user = User.builder()
                .username("anyString")
                .password("anyString")
                .roles("ADMIN")
                .build();
        return jwtService.generateToken(user);
    }

    @Test
    void testThatSaveAuthorReturnsHttpStatusCode201Created() throws Exception {
        when(authorService.saveAuthor(any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorEntity(anyValidAuthorDto())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String authorJson = objectMapper.writeValueAsString(anyValidAuthorDto());

        mockMvc.perform(post("/api/author/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(authorJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("dummy"))
                .andExpect(jsonPath("$.lastName").value("dummy"));
    }

    @Test
    void testThatSaveAuthorReturnsHttpStatusCode400BadRequestWhenFirstNameIsBlank() throws Exception {
        AuthorEntity authorEntity = anyValidAuthorEntity();
        authorEntity.setFirstName("");

        AuthorDto authorDto = anyValidAuthorDto();
        authorDto.setFirstName("");

        when(authorService.saveAuthor(any(AuthorEntity.class))).thenReturn(authorEntity);
        when(authorMapper.toAuthorEntity(authorDto)).thenReturn(authorEntity);
        when(authorMapper.toAuthorDto(authorEntity)).thenReturn(authorDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(post("/api/author/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(authorJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveAuthorReturnsHttpStatusCode400BadRequestWhenFirstNameIsNull() throws Exception {
        AuthorEntity authorEntity = anyValidAuthorEntity();
        authorEntity.setFirstName(null);

        AuthorDto authorDto = anyValidAuthorDto();
        authorDto.setFirstName(null);

        when(authorService.saveAuthor(any(AuthorEntity.class))).thenReturn(authorEntity);
        when(authorMapper.toAuthorEntity(authorDto)).thenReturn(authorEntity);
        when(authorMapper.toAuthorDto(authorEntity)).thenReturn(authorDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(post("/api/author/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(authorJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveAuthorReturnsHttpStatusCode400BadRequestWhenLastNameIsBlank() throws Exception {
        AuthorEntity authorEntity = anyValidAuthorEntity();
        authorEntity.setLastName("");

        AuthorDto authorDto = anyValidAuthorDto();
        authorDto.setLastName("");

        when(authorService.saveAuthor(any(AuthorEntity.class))).thenReturn(authorEntity);
        when(authorMapper.toAuthorEntity(authorDto)).thenReturn(authorEntity);
        when(authorMapper.toAuthorDto(authorEntity)).thenReturn(authorDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(post("/api/author/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(authorJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatSaveAuthorReturnsHttpStatusCode400BadRequestWhenLastNameIsNull() throws Exception {
        AuthorEntity authorEntity = anyValidAuthorEntity();
        authorEntity.setLastName(null);

        AuthorDto authorDto = anyValidAuthorDto();
        authorDto.setLastName(null);

        when(authorService.saveAuthor(any(AuthorEntity.class))).thenReturn(authorEntity);
        when(authorMapper.toAuthorEntity(authorDto)).thenReturn(authorEntity);
        when(authorMapper.toAuthorDto(authorEntity)).thenReturn(authorDto);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(post("/api/author/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(authorJson)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void testThatGetAuthorByIdReturnsHttpStatusCode200Ok() throws Exception {
        when(authorService.getAuthorById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(authorMapper.toAuthorEntity(anyValidAuthorDto())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetAuthorByIdReturnsHttpStatusCode404NotFound() throws Exception {
        when(authorService.getAuthorById(anyLong())).thenReturn(Optional.empty());
        when(authorMapper.toAuthorEntity(anyValidAuthorDto())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testThatGetAllAuthorsReturnsHttpStatusCode200Ok() throws Exception {
        when(authorService.getAllAuthors(anyInt(), anyInt())).thenReturn(Collections.singletonList(anyValidAuthorEntity()));
        when(authorMapper.toAuthorEntity(anyValidAuthorDto())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/all/simple")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetAllAuthorsWithSortPropertyReturnsHttpStatusCode200Ok() throws Exception {
        when(authorService.getAllAuthors(anyInt(), anyInt(), anyString())).thenReturn(Collections.singletonList(anyValidAuthorEntity()));
        when(authorMapper.toAuthorEntity(anyValidAuthorDto())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/all/bySort")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortProperty", "id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatUpdateAuthorReturnsHttpStatusCode200Ok() throws Exception {
        when(authorService.updateAuthor(anyLong(), any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorEntity(anyValidAuthorDto())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String authorJson = objectMapper.writeValueAsString(anyValidAuthorDto());

        mockMvc.perform(put("/api/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(authorJson)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatPartialUpdateReturnsHttpStatusCode200Ok() throws Exception {
        when(authorService.partialUpdate(anyLong(), any())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorEntity(anyValidAuthorDto())).thenReturn(anyValidAuthorEntity());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        String authorJson = objectMapper.writeValueAsString(anyValidAuthorDto());

        mockMvc.perform(patch("/api/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                        .content(authorJson)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatDeleteAuthorReturnsHttpStatusCode204NoContent() throws Exception {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(delete("/api/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void testThatGetNumberOfAuthorsReturnsHttpStatusCode200Ok() throws Exception {
        Map<String, Long> map = new HashMap<>();
        map.put("Number of Authors", authorService.getNumberOfAuthors());

        when(authorService.getNumberOfAuthors()).thenReturn(map.get("Number of Authors"));
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetAuthorByFirstNameReturnsHttpStatusCode200Ok() throws Exception {
        when(authorService.getAuthorByFirstName(anyString())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/filter/name")
                        .queryParam("firstName", "dummy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

    @Test
    void testThatGetAuthorByFirstNameReturnsHttpStatusCode404NotFound() throws Exception {
        when(authorService.getAuthorByFirstName(anyString())).thenReturn(Optional.empty());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/filter/name")
                        .queryParam("firstName", "dummy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testThatGetAuthorByAgeBetweenReturnsHttpStatusCode200Ok() throws Exception {
        when(authorService.getAuthorByAgeBetween(anyInt(), anyInt())).thenReturn(List.of(anyValidAuthorEntity()));
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(authorMapper.toAuthorDto(anyValidAuthorEntity())).thenReturn(anyValidAuthorDto());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(anyValidUserDetails());

        String token = generateToken();

        mockMvc.perform(get("/api/author/filter/age")
                        .queryParam("minAge", "20")
                        .queryParam("maxAge", "30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-requested-area", "dev")
                        .header("Authorization", BEARER + token)
                )
                .andExpect(status().isOk());
    }

}