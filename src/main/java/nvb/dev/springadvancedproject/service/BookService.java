package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.model.BookEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {

    BookEntity saveBook(long authorId, BookEntity bookEntity);

    Optional<BookEntity> getBookById(long bookId);

    Iterable<BookEntity> getAllBooks(int page, int size);

    Iterable<BookEntity> getAllBooks(int page, int size, String sortProperty);

    List<BookEntity> getBooksByAuthorId(long authorId);

    BookEntity updateBook(long bookId, long authorId, BookEntity bookEntity);

    BookEntity partialUpdate(long bookId, long authorId, Map<String, Object> bookEntity);

    void deleteBookByAuthorId(long authorId);

}
