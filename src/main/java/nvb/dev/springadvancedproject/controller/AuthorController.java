package nvb.dev.springadvancedproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.AuthorDto;
import nvb.dev.springadvancedproject.mapper.AuthorMapper;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping(path = "/save")
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody @Valid AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(authorDto);
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.toAuthorDto(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.getAuthorById(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.toAuthorDto(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all/simple")
    public ResponseEntity<Iterable<AuthorDto>> getAllAuthors(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Iterable<AuthorEntity> allAuthors = authorService.getAllAuthors(page, size);
        Iterable<AuthorDto> authorDtoIterator = StreamSupport.stream(allAuthors.spliterator(), false)
                .map(authorMapper::toAuthorDto).toList();
        return new ResponseEntity<>(authorDtoIterator, HttpStatus.OK);
    }

    @GetMapping(path = "/all/bySort")
    public ResponseEntity<Iterable<AuthorDto>> getAllAuthors(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortProperty
    ) {
        Iterable<AuthorEntity> allAuthors = authorService.getAllAuthors(page, size, sortProperty);
        List<AuthorDto> authorDtoList = StreamSupport.stream(allAuthors.spliterator(), false)
                .map(authorMapper::toAuthorDto).toList();
        return new ResponseEntity<>(authorDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(authorDto);
        AuthorEntity updatedAuthor = authorService.updateAuthor(id, authorEntity);
        return new ResponseEntity<>(authorMapper.toAuthorDto(updatedAuthor), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid Map<String, Object> authorDto) {
        AuthorEntity authorEntity = authorService.partialUpdate(id, authorDto);
        return new ResponseEntity<>(authorMapper.toAuthorDto(authorEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

}
