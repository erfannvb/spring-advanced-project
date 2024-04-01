package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.exception.AuthorNotFoundException;
import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.NoAuthorsFoundException;
import nvb.dev.springadvancedproject.exception.WrongSortingPropertyException;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.repository.AuthorRepository;
import nvb.dev.springadvancedproject.service.AuthorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorEntity saveAuthor(AuthorEntity authorEntity) {
        try {
            return authorRepository.save(authorEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityNotStorableException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#id", value = "author")
    public Optional<AuthorEntity> getAuthorById(Long id) {
        return Optional.ofNullable(authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "authorList")
    public Iterable<AuthorEntity> getAllAuthors(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("Page must be greater than 0.");
        if (size < 1) throw new IllegalArgumentException("Size must be greater than 0.");
        Page<AuthorEntity> result = authorRepository.findAll(PageRequest.of(page, size));
        if (page > result.getTotalPages()) throw new IllegalArgumentException(
                STR."For the given size the page must be less than/equal to \{result.getTotalPages()}."
        );
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "authorList")
    public Iterable<AuthorEntity> getAllAuthors(int page, int size, String sortProperty) {
        if (page < 0) throw new IllegalArgumentException("Page must be greater than 0.");
        if (size < 1) throw new IllegalArgumentException("Size must be greater than 0.");
        try {
            return authorRepository.findAll(PageRequest.of(page, size, Sort.by(sortProperty)));
        } catch (PropertyReferenceException e) {
            throw new WrongSortingPropertyException(sortProperty, "id, firstName, lastName, age");
        }
    }

    @Override
    public AuthorEntity updateAuthor(Long id, AuthorEntity authorEntity) {
        Optional<AuthorEntity> foundAuthor = authorRepository.findById(id);
        if (foundAuthor.isPresent()) {

            AuthorEntity existingAuthor = foundAuthor.get();
            existingAuthor.setFirstName(authorEntity.getFirstName());
            existingAuthor.setLastName(authorEntity.getLastName());
            existingAuthor.setAge(authorEntity.getAge());

            return authorRepository.save(existingAuthor);

        } else {
            throw new AuthorNotFoundException();
        }
    }

    @Override
    public AuthorEntity partialUpdate(Long id, Map<String, Object> authorEntity) {
        Optional<AuthorEntity> foundAuthor = authorRepository.findById(id);
        if (foundAuthor.isPresent()) {
            authorEntity.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AuthorEntity.class, key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, foundAuthor.get(), value);
            });
            return authorRepository.save(foundAuthor.get());
        }
        throw new AuthorNotFoundException();
    }

    @Override
    @CacheEvict(key = "#id", value = "author", beforeInvocation = true)
    public void deleteAuthor(Long id) {
        authorRepository.findById(id)
                .ifPresentOrElse(
                        _ -> authorRepository.deleteById(id),
                        () -> {
                            throw new AuthorNotFoundException();
                        }
                );
    }

    @Override
    public long getNumberOfAuthors() {
        List<AuthorEntity> authorEntityList = authorRepository.findAll();
        if (authorEntityList.isEmpty()) throw new NoAuthorsFoundException();
        return authorEntityList.size();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorEntity> getAuthorByFirstName(String firstName) {
        return Optional.ofNullable(authorRepository.findByFirstNameIgnoreCase(firstName)
                .orElseThrow(AuthorNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> getAuthorByAgeBetween(int minAge, int maxAge) {
        List<AuthorEntity> authorEntityList = authorRepository.findAll();
        if (authorEntityList.isEmpty()) throw new NoAuthorsFoundException();
        return authorRepository.findByAgeBetween(minAge, maxAge);
    }
}
