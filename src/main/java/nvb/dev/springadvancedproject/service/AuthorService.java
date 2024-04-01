package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.model.AuthorEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity saveAuthor(AuthorEntity authorEntity);

    Optional<AuthorEntity> getAuthorById(Long id);

    Iterable<AuthorEntity> getAllAuthors(int page, int size);

    Iterable<AuthorEntity> getAllAuthors(int page, int size, String sortProperty);

    AuthorEntity updateAuthor(Long id, AuthorEntity authorEntity);

    AuthorEntity partialUpdate(Long id, Map<String, Object> authorEntity);

    void  deleteAuthor(Long id);

    long getNumberOfAuthors();

    Optional<AuthorEntity> getAuthorByFirstName(String firstName);

    List<AuthorEntity> getAuthorByAgeBetween(int minAge, int maxAge);

}
