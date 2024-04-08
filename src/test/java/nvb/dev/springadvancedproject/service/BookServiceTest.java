package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.WrongSortingPropertyException;
import nvb.dev.springadvancedproject.exception.author.AuthorNotFoundException;
import nvb.dev.springadvancedproject.exception.book.BookNotFoundException;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.repository.AuthorRepository;
import nvb.dev.springadvancedproject.repository.BookRepository;
import nvb.dev.springadvancedproject.service.impl.BookServiceImpl;
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
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void testThatSaveBookWithExistingAuthorCanSaveTheBook() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        BookEntity savedBook = bookService.saveBook(anyValidAuthorEntity().getId(), anyValidBookEntity());

        assertEquals("anyString", savedBook.getTitle());
        assertEquals("anyString", savedBook.getIsbn());

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).save(any(BookEntity.class));
    }

    @Test
    void testThatSaveBookDoesNotSaveWhenAuthorDoesNotExist() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () ->
                bookService.saveBook(12L, anyValidBookEntity()));

        assertEquals("Author with id '12' does not exist.", exception.getMessage());
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    void testThatSaveBookThrowsEntityNotStorableException() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(bookRepository.save(any(BookEntity.class))).thenThrow(DataIntegrityViolationException.class);

        EntityNotStorableException exception = assertThrows(EntityNotStorableException.class, () ->
                bookService.saveBook(123L, anyValidBookEntity()));

        assertEquals("There was an error in storing the data.", exception.getMessage());

        verify(authorRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testGetBookByIdReturnsTheExistingBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));

        BookEntity bookEntity = bookService.getBookById(123L).orElseThrow();

        assertEquals("anyString", bookEntity.getTitle());
        assertEquals("anyString", bookEntity.getIsbn());

        verify(bookRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testGetBookByIdThrowsBookNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () ->
                bookService.getBookById(1L));

        assertEquals("Book with id '1' does not exist.", exception.getMessage());
        verify(bookRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testThatGetAllBooksWithValidPageAndSizeReturnsAllBooks() {
        List<BookEntity> books = Collections.singletonList(anyValidBookEntity());
        Page<BookEntity> pageResult = new PageImpl<>(books);

        when(bookRepository.findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE))).thenReturn(pageResult);

        Iterable<BookEntity> result = bookService.getAllBooks(ANY_VALID_PAGE, ANY_VALID_SIZE);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());

        assertEquals("anyString", result.iterator().next().getTitle());
        assertEquals("anyString", result.iterator().next().getIsbn());

        verify(bookRepository, atLeastOnce()).findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE));
    }

    @Test
    void testThatGetAllBooksWithNegativePageThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookService.getAllBooks(ANY_INVALID_PAGE, ANY_VALID_SIZE));
        assertEquals("Page must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllBooksWithInvalidSizeThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookService.getAllBooks(ANY_VALID_PAGE, ANY_INVALID_SIZE));
        assertEquals("Size must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllBooksWithPageGreaterThanTotalPagesThrowsIllegalArgumentException() {
        Page<BookEntity> pageResult = new PageImpl<>(Collections.emptyList());
        when(bookRepository.findAll(PageRequest.of(ANOTHER_INVALID_PAGE, ANY_VALID_SIZE))).thenReturn(pageResult);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookService.getAllBooks(ANOTHER_INVALID_PAGE, ANY_VALID_SIZE));
        assertEquals("For the given size the page must be less than/equal to 1.", exception.getMessage());
    }

    @Test
    void testGetAllBooksWithValidPageAndSizeAndSortPropertyReturnsAllBooks() {
        List<BookEntity> books = Collections.singletonList(anyValidBookEntity());
        Page<BookEntity> pageResult = new PageImpl<>(books);

        when(bookRepository.findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE, Sort.by(ANY_VALID_SORT_PROPERTY))))
                .thenReturn(pageResult);

        Iterable<BookEntity> result = bookService.getAllBooks(ANY_VALID_PAGE, ANY_VALID_SIZE, ANY_VALID_SORT_PROPERTY);

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());

        assertEquals("anyString", result.iterator().next().getTitle());
        assertEquals("anyString", result.iterator().next().getIsbn());

        verify(bookRepository, atLeastOnce()).findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE, Sort.by(ANY_VALID_SORT_PROPERTY)));
    }

    @Test
    void testThatGetAllBooksBySortWithNegativePageThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookService.getAllBooks(ANY_INVALID_PAGE, ANY_VALID_SIZE, ANY_VALID_SORT_PROPERTY));
        assertEquals("Page must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllBooksBySortWithInvalidSizeThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookService.getAllBooks(ANY_VALID_PAGE, ANY_INVALID_SIZE, ANY_VALID_SORT_PROPERTY));
        assertEquals("Size must be greater than 0.", exception.getMessage());
    }

    @Test
    void testThatGetAllBooksWithInvalidSortPropertyThrowsWrongSortingPropertyException() {
        when(bookRepository.findAll(PageRequest.of(ANY_VALID_PAGE, ANY_VALID_SIZE, Sort.by(ANY_INVALID_SORT_PROPERTY))))
                .thenThrow(PropertyReferenceException.class);

        WrongSortingPropertyException exception = assertThrows(WrongSortingPropertyException.class, () ->
                bookService.getAllBooks(ANY_VALID_PAGE, ANY_VALID_SIZE, ANY_INVALID_SORT_PROPERTY));
        assertEquals("Unable to sort by property 'invalidProperty'. Use one of the following:" +
                " id, title, isbn, pages, rating, genres, publishedYear.", exception.getMessage());
    }

    @Test
    void testGetBooksByAuthorIdReturnsListOfBooks() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(bookRepository.findBookByAuthorId(anyLong())).thenReturn(List.of(anyValidBookEntity()));

        List<BookEntity> booksByAuthorId = bookService.getBooksByAuthorId(123L);

        assertEquals("anyString", booksByAuthorId.getFirst().getTitle());
        assertEquals("anyString", booksByAuthorId.getFirst().getIsbn());

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).findBookByAuthorId(anyLong());
    }

    @Test
    void testGetBooksByAuthorIdThrowsAuthorNotFoundException() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> bookService.getBooksByAuthorId(1L));
        verify(authorRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testUpdateBookUpdatesTheExistingBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        BookEntity bookEntity = bookService.updateBook(1L, 1L, anyValidBookEntity());

        assertEquals("anyString", bookEntity.getTitle());
        assertEquals("anyString", bookEntity.getIsbn());

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).save(any(BookEntity.class));
    }

    @Test
    void testUpdateBookThrowsBookNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        assertThrows(BookNotFoundException.class, () ->
                bookService.updateBook(1L, 1L, anyValidBookEntity()));

        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, never()).findById(anyLong());
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    void testUpdateBookThrowsAuthorNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        assertThrows(AuthorNotFoundException.class, () ->
                bookService.updateBook(1L, 1L, anyValidBookEntity()));

        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    void testPartialUpdateCanUpdateTheExistingBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        BookEntity bookEntity = bookService.partialUpdate(1L, 1L, anyValidBookMap());

        assertEquals("anyString", bookEntity.getTitle());
        assertEquals("anyString", bookEntity.getIsbn());

        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).save(any(BookEntity.class));
    }

    @Test
    void testPartialUpdateThrowsBookNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        assertThrows(BookNotFoundException.class, () ->
                bookService.partialUpdate(1L, 1L, anyValidBookMap()));

        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, never()).findById(anyLong());
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    void testPartialUpdateThrowsAuthorNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.save(any(BookEntity.class))).thenReturn(anyValidBookEntity());

        assertThrows(AuthorNotFoundException.class, () -> bookService.partialUpdate(1L, 1L, anyValidBookMap()));

        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    void testDeleteBookByAuthorIdDeletesTheBookOfTheExistingAuthor() {
        long authorId = ANY_ID;
        long bookId = ANY_ID;

        AuthorEntity authorEntity = anyValidAuthorEntity();
        BookEntity bookEntity = anyValidBookEntity();
        authorEntity.getBooks().add(bookEntity);

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorEntity));

        bookService.deleteBookByAuthorId(bookId, authorId);

        verify(bookRepository, atLeastOnce()).delete(bookEntity);
        assertNull(bookEntity.getAuthor());
    }

    @Test
    void testDeleteBookByAuthorIdThrowsAuthorNotFoundException() {
        long authorId = ANY_ID;
        long bookId = ANY_ID;

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> bookService.deleteBookByAuthorId(bookId, authorId));

        verify(authorRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, never()).delete(any());
    }

    @Test
    void testDeleteBookByAuthorIdThrowsBookNotFoundException() {
        long authorId = ANY_ID;
        long bookId = ANY_ID;

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(anyValidAuthorEntity()));

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookByAuthorId(bookId, authorId));

        verify(bookRepository, never()).save(any());
    }

}