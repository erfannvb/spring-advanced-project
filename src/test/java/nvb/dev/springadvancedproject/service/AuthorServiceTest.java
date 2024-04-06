package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.WrongSortingPropertyException;
import nvb.dev.springadvancedproject.exception.author.AuthorNotFoundException;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.repository.AuthorRepository;
import nvb.dev.springadvancedproject.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AuthorServiceTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    void testThatSaveAuthorCanSaveAuthor() {
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());

        AuthorEntity savedAuthor = authorService.saveAuthor(anyValidAuthorEntity());

        assertEquals("dummy", savedAuthor.getFirstName());
        assertEquals("dummy", savedAuthor.getLastName());

        verify(authorRepository, atLeastOnce()).save(any(AuthorEntity.class));
    }

    @Test
    void testThatSaveAuthorThrowsEntityNotStorableException() {
        when(authorRepository.save(any(AuthorEntity.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(EntityNotStorableException.class, () -> authorService.saveAuthor(anyValidAuthorEntity()));
        verify(authorRepository, atLeastOnce()).save(any(AuthorEntity.class));
    }

    @Test
    void testThatGetAuthorByIdReturnsTheExistingAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));

        AuthorEntity authorEntity = authorService.getAuthorById(123L).orElseThrow();

        assertEquals("dummy", authorEntity.getFirstName());
        assertEquals("dummy", authorEntity.getLastName());

        verify(authorRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetAuthorByIdThrowsAuthorNotFoundException() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(1L));
        verify(authorRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetAllAuthorsWithValidPageAndSizeReturnsAllAuthors() {
        List<AuthorEntity> authors = Collections.singletonList(anyValidAuthorEntity());
        Page<AuthorEntity> pageResult = new PageImpl<>(authors);

        when(authorRepository.findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE))).thenReturn(pageResult);

        Iterable<AuthorEntity> result = authorService.getAllAuthors(ANY_VALID_PAGE, ANY_VALID_SIZE);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());

        assertEquals("dummy", result.iterator().next().getFirstName());
        assertEquals("dummy", result.iterator().next().getLastName());

        verify(authorRepository, atLeastOnce()).findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE));
    }

    @Test
    void testThatGetAllAuthorsWithNegativePageThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorService.getAllAuthors(ANY_INVALID_PAGE, ANY_VALID_SIZE));

        assertEquals("Page must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllAuthorsWithInvalidSizeThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorService.getAllAuthors(ANY_VALID_PAGE, ANY_INVALID_SIZE));

        assertEquals("Size must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllAuthorsWithPageGreaterThanTotalPagesThrowsIllegalArgumentException() {
        Page<AuthorEntity> pageResult = new PageImpl<>(Collections.emptyList());
        when(authorRepository.findAll(PageRequest.of(ANOTHER_INVALID_PAGE, ANY_VALID_SIZE))).thenReturn(pageResult);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorService.getAllAuthors(ANOTHER_INVALID_PAGE, ANY_VALID_SIZE));

        assertEquals("For the given size the page must be less than/equal to 1.", exception.getMessage());
    }

    @Test
    void testGetAllAuthorsWithValidPageAndSizeAndSortPropertyReturnsAllAuthors() {
        List<AuthorEntity> authors = Collections.singletonList(anyValidAuthorEntity());
        Page<AuthorEntity> pageResult = new PageImpl<>(authors);

        when(authorRepository.findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE, Sort.by(ANY_VALID_SORT_PROPERTY))))
                .thenReturn(pageResult);

        Iterable<AuthorEntity> result = authorService.getAllAuthors(ANY_VALID_PAGE, ANY_VALID_SIZE, ANY_VALID_SORT_PROPERTY);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());

        assertEquals("dummy", result.iterator().next().getFirstName());
        assertEquals("dummy", result.iterator().next().getLastName());

        verify(authorRepository, atLeastOnce()).findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE, Sort.by(ANY_VALID_SORT_PROPERTY)));
    }

    @Test
    void testThatGetAllAuthorsBySortWithNegativePageThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorService.getAllAuthors(ANY_INVALID_PAGE, ANY_VALID_SIZE, ANY_VALID_SORT_PROPERTY));
        assertEquals("Page must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllAuthorsBySortWithInvalidSizeThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authorService.getAllAuthors(ANY_VALID_PAGE, ANY_INVALID_SIZE, ANY_VALID_SORT_PROPERTY));
        assertEquals("Size must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllAuthorsWithInvalidSortPropertyThrowsWrongSortingPropertyException() {
        when(authorRepository.findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE, Sort.by(ANY_INVALID_SORT_PROPERTY))))
                .thenThrow(PropertyReferenceException.class);

        WrongSortingPropertyException exception = assertThrows(WrongSortingPropertyException.class, () ->
                authorService.getAllAuthors(ANY_VALID_PAGE, ANY_VALID_SIZE, ANY_INVALID_SORT_PROPERTY));
        assertEquals("Unable to sort by property 'invalidProperty'. Use one of the following: id, firstName, lastName, age."
                , exception.getMessage());
    }

    @Test
    void testThatUpdateAuthorUpdatesTheExistingAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());

        AuthorEntity updatedAuthor = authorService.updateAuthor(123L, anyValidAuthorEntity());

        assertEquals("dummy", updatedAuthor.getFirstName());
        assertEquals("dummy", updatedAuthor.getLastName());

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, atLeastOnce()).save(any(AuthorEntity.class));
    }

    @Test
    void testThatUpdateAuthorDoesNotUpdateTheNonExistingAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () ->
                authorService.updateAuthor(123L, anyValidAuthorEntity()));

        assertEquals("Author with id '123' does not exist.", exception.getMessage());
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, never()).save(any(AuthorEntity.class));
    }

    @Test
    void testThatPartialUpdateCanUpdateTheExistingAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());

        AuthorEntity authorEntity = authorService.partialUpdate(123L, anyValidAuthorMap());

        assertEquals("dummy", authorEntity.getFirstName());
        assertEquals("dummy", authorEntity.getLastName());

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, atLeastOnce()).save(any(AuthorEntity.class));
    }

    @Test
    void testThatPartialUpdateCannotUpdateTheNonExistingAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () ->
                authorService.partialUpdate(123L, anyValidAuthorMap()));

        assertEquals("Author with id '123' does not exist.", exception.getMessage());
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, never()).save(any(AuthorEntity.class));
    }

    @Test
    void testThatDeleteAuthorCanDeleteTheExistingAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(anyValidAuthorEntity());

        authorService.deleteAuthor(123L);
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testThatDeleteAuthorCannotDeleteTheNonExistingAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.deleteAuthor(12L));
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testThatGetNumberOfAuthorsReturnsTheNumberOfAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(anyValidAuthorEntity(), anyValidAuthorEntity()));

        long numberOfAuthors = authorService.getNumberOfAuthors();
        assertEquals(2, numberOfAuthors);
        verify(authorRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAuthorsReturnsZeroWhenThereAreNoAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () ->
                authorService.getNumberOfAuthors());
        assertEquals("There is no author in the database.", exception.getMessage());
        verify(authorRepository, atLeastOnce()).findAll();
    }

    @Test
    void testThatGetAuthorByFirstNameReturnsTheExistingAuthor() {
        when(authorRepository.findByFirstNameIgnoreCase(anyString())).thenReturn(Optional.of(anyValidAuthorEntity()));

        AuthorEntity authorEntity = authorService.getAuthorByFirstName("dummy").orElseThrow();

        assertEquals("dummy", authorEntity.getFirstName());
        assertEquals("dummy", authorEntity.getLastName());

        verify(authorRepository, atLeastOnce()).findByFirstNameIgnoreCase(anyString());
    }

    @Test
    void testThatGetAuthorByFirstNameThrowsAuthorNotFoundException() {
        when(authorRepository.findByFirstNameIgnoreCase(anyString())).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () ->
                authorService.getAuthorByFirstName("dummy"));
        assertEquals("'dummy' does not exist.", exception.getMessage());
        verify(authorRepository, atLeastOnce()).findByFirstNameIgnoreCase(anyString());
    }

    @Test
    void testThatGetAuthorByAgeBetweenReturnsListOfAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(anyValidAuthorEntity()));
        when(authorRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(List.of(anyValidAuthorEntity()));

        List<AuthorEntity> authorByAgeBetween = authorService.getAuthorByAgeBetween(18, 50);

        assertEquals("dummy", authorByAgeBetween.getFirst().getFirstName());
        assertEquals("dummy", authorByAgeBetween.getFirst().getLastName());

        verify(authorRepository, atLeastOnce()).findByAgeBetween(anyInt(), anyInt());
    }

    @Test
    void testThatGetAuthorByAgeThrowsAuthorNotFoundException() {
        when(authorRepository.findAll()).thenReturn(List.of());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () ->
                authorService.getAuthorByAgeBetween(30, 50));

        assertEquals("There is no author in the database.", exception.getMessage());
        verify(authorRepository, atLeastOnce()).findAll();
        verify(authorRepository, never()).findByAgeBetween(anyInt(), anyInt());
    }

}