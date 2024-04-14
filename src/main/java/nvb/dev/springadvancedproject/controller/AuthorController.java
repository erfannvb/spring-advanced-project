package nvb.dev.springadvancedproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.AuthorDto;
import nvb.dev.springadvancedproject.mapper.AuthorMapper;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/author")
@RequiredArgsConstructor
@Validated
@Tag(name = "Author Controller", description = "Performing Crud Operations on Authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Operation(summary = "Create Author", description = "Creates an Author from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Creation of Author"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PostMapping(path = "/save")
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody @Valid AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(authorDto);
        AuthorEntity savedAuthor = authorService.saveAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.toAuthorDto(savedAuthor), HttpStatus.CREATED);
    }

    @Operation(summary = "Get author by ID", description = "Returns an author based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Retrieval of Author"),
            @ApiResponse(responseCode = "404", description = "Author Not Found")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        Optional<AuthorEntity> foundAuthor = authorService.getAuthorById(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.toAuthorDto(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all authors with page and size", description = "Provides a list of all authors")
    @ApiResponse(responseCode = "200", description = "Successful Retrieval of Authors")
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

    @Operation(summary = "Get all authors with page, size and sort property", description = "Provides a list of all authors")
    @ApiResponse(responseCode = "200", description = "Successful Retrieval of Authors")
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

    @Operation(summary = "Update Author", description = "Update author based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating of Author"),
            @ApiResponse(responseCode = "404", description = "Author Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(authorDto);
        AuthorEntity updatedAuthor = authorService.updateAuthor(id, authorEntity);
        return new ResponseEntity<>(authorMapper.toAuthorDto(updatedAuthor), HttpStatus.OK);
    }

    @Operation(summary = "Partial Update Author", description = "Update author based on an optional field")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Partial Updating of Author"),
            @ApiResponse(responseCode = "404", description = "Author Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PatchMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable Long id, @RequestBody @Valid Map<String, Object> authorDto) {
        AuthorEntity authorEntity = authorService.partialUpdate(id, authorDto);
        return new ResponseEntity<>(authorMapper.toAuthorDto(authorEntity), HttpStatus.OK);
    }

    @Operation(summary = "Delete Author By ID", description = "Delete an author based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of author"),
            @ApiResponse(responseCode = "404", description = "Author does not exist")
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Number of Authors", description = "Get Number of Saved Authors")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of authors count")
    @GetMapping(path = "/count")
    public ResponseEntity<Map<String, Long>> getNumberOfAuthors() {
        Map<String, Long> authors = new HashMap<>();
        authors.put("Number of Authors", authorService.getNumberOfAuthors());
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Operation(summary = "Get Author by FirstName", description = "Get author by their first name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of author"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping(path = "/filter/name")
    public ResponseEntity<AuthorDto> getAuthorByFirstName(@RequestParam String firstName) {
        Optional<AuthorEntity> authorByFirstName = authorService.getAuthorByFirstName(firstName);
        return authorByFirstName.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.toAuthorDto(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get list of authors by their age", description = "Get list of authors based on their age range")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of authors")
    @GetMapping(path = "/filter/age")
    public ResponseEntity<List<AuthorDto>> getAuthorByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        List<AuthorEntity> authorByAgeBetween = authorService.getAuthorByAgeBetween(minAge, maxAge);
        List<AuthorDto> authorDtoList = authorByAgeBetween.stream().map(authorMapper::toAuthorDto).toList();
        return new ResponseEntity<>(authorDtoList, HttpStatus.OK);
    }

}
