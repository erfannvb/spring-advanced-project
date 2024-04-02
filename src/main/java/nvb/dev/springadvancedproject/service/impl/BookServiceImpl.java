package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.exception.AuthorNotFoundException;
import nvb.dev.springadvancedproject.exception.BookNotFoundException;
import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.WrongSortingPropertyException;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.repository.AuthorRepository;
import nvb.dev.springadvancedproject.repository.BookRepository;
import nvb.dev.springadvancedproject.service.BookService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookEntity saveBook(long authorId, BookEntity bookEntity) {
        try {
            AuthorEntity author = unwrapAuthor(authorRepository.findById(authorId), authorId);
            bookEntity.setAuthor(author);
            return bookRepository.save(bookEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotStorableException();
        }
    }

    @Override
    public Optional<BookEntity> getBookById(long bookId) {
        return Optional.ofNullable(bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new));
    }

    @Override
    public Iterable<BookEntity> getAllBooks(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("Page must be greater than 0.");
        if (size < 1) throw new IllegalArgumentException("Size must be greater than 0.");
        Page<BookEntity> result = bookRepository.findAll(PageRequest.of(page, size));
        if (page > result.getTotalPages()) throw new IllegalArgumentException(
                "For the given size the page must be less than/equal to %d".formatted(result.getTotalPages())
        );
        return result;
    }

    @Override
    public Iterable<BookEntity> getAllBooks(int page, int size, String sortProperty) {
        if (page < 0) throw new IllegalArgumentException("Page must be greater than 0.");
        if (size < 1) throw new IllegalArgumentException("Size must be greater than 0.");
        try {
            return bookRepository.findAll(PageRequest.of(page, size, Sort.by(sortProperty)));
        } catch (PropertyReferenceException e) {
            throw new WrongSortingPropertyException(sortProperty, "id, title, isbn, pages, rating, genres, publishedYear");
        }
    }

    @Override
    public List<BookEntity> getBooksByAuthorId(long authorId) {
        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorId);
        if (authorEntity.isEmpty()) throw new AuthorNotFoundException(authorId);
        return bookRepository.findBookByAuthorId(authorId);
    }

    @Override
    public BookEntity updateBook(long bookId, long authorId, BookEntity bookEntity) {
        Optional<BookEntity> foundBookOptional = bookRepository.findById(bookId);
        if (foundBookOptional.isPresent()) {
            BookEntity existingBook = foundBookOptional.get();

            existingBook.setTitle(bookEntity.getTitle());
            existingBook.setIsbn(bookEntity.getIsbn());
            existingBook.setPages(bookEntity.getPages());
            existingBook.setRating(bookEntity.getRating());
            existingBook.setGenres(bookEntity.getGenres());
            existingBook.setPublishedYear(bookEntity.getPublishedYear());

            if (authorId != 0) {
                Optional<AuthorEntity> updatedAuthorOptional = authorRepository.findById(authorId);
                if (updatedAuthorOptional.isPresent()) {
                    existingBook.setAuthor(updatedAuthorOptional.get());
                } else {
                    throw new AuthorNotFoundException(authorId);
                }
            }

            return bookRepository.save(existingBook);

        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public BookEntity partialUpdate(long bookId, long authorId, Map<String, Object> bookEntity) {
        Optional<BookEntity> foundBookOptional = bookRepository.findById(bookId);
        if (foundBookOptional.isPresent()) {
            BookEntity existingBook = foundBookOptional.get();

            bookEntity.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(BookEntity.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, existingBook, value);
                }
            });

            if (authorId != 0) {
                Optional<AuthorEntity> updatedAuthorOptional = authorRepository.findById(authorId);
                if (updatedAuthorOptional.isPresent()) {
                    existingBook.setAuthor(updatedAuthorOptional.get());
                } else {
                    throw new AuthorNotFoundException(authorId);
                }
            }

            return bookRepository.save(existingBook);

        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public void deleteBookByAuthorId(long authorId) {
        Optional<AuthorEntity> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isPresent()) {
            bookRepository.deleteBookByAuthorId(authorId);
        } else {
            throw new AuthorNotFoundException(authorId);
        }
    }

    private static AuthorEntity unwrapAuthor(Optional<AuthorEntity> entity, long authorId) {
        if (entity.isPresent()) return entity.get();
        else throw new AuthorNotFoundException(authorId);
    }
}
