package nvb.dev.springadvancedproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.BookDto;
import nvb.dev.springadvancedproject.mapper.BookMapper;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/book")
@RequiredArgsConstructor
@Validated
@Tag(name = "Book Controller", description = "Performing Crud Operations on Books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Operation(summary = "Create Book", description = "Creates a book from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Creation of Book"),
            @ApiResponse(responseCode = "404", description = "Author Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PostMapping(path = "/save/{authorId}")
    public ResponseEntity<BookDto> saveBook(@PathVariable long authorId, @RequestBody @Valid BookDto bookDto) {
        BookEntity bookEntity = bookMapper.toBookEntity(bookDto);
        BookEntity savedBook = bookService.saveBook(authorId, bookEntity);
        return new ResponseEntity<>(bookMapper.toBookDto(savedBook), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Book by ID", description = "Returns a book based on an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Retrieval of Book"),
            @ApiResponse(responseCode = "404", description = "Book Not Found")
    })
    @GetMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long bookId) {
        Optional<BookEntity> bookById = bookService.getBookById(bookId);
        return bookById.map(bookEntity -> {
            BookDto bookDto = bookMapper.toBookDto(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all books with page and size", description = "Provides a list of all books")
    @ApiResponse(responseCode = "200", description = "Successful Retrieval of Books")
    @GetMapping(path = "/all/simple")
    public ResponseEntity<Iterable<BookDto>> getAllBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Iterable<BookEntity> allBooks = bookService.getAllBooks(page, size);
        List<BookDto> bookDtoList = StreamSupport.stream(allBooks.spliterator(), false)
                .map(bookMapper::toBookDto).toList();
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Get all books with page, size and sort property", description = "Provides a list of all books")
    @ApiResponse(responseCode = "200", description = "Successful Retrieval of Books")
    @GetMapping(path = "/all/bySort")
    public ResponseEntity<Iterable<BookDto>> getAllBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortProperty
    ) {
        Iterable<BookEntity> allBooks = bookService.getAllBooks(page, size, sortProperty);
        List<BookDto> bookDtoList = StreamSupport.stream(allBooks.spliterator(), false)
                .map(bookMapper::toBookDto).toList();
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Get Book by Author ID", description = "Returns a book based on an Author ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Retrieval of Book"),
            @ApiResponse(responseCode = "404", description = "Author Not Found")
    })
    @GetMapping(path = "/author/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthorId(@PathVariable long authorId) {
        List<BookEntity> booksByAuthorId = bookService.getBooksByAuthorId(authorId);
        List<BookDto> bookDtoList = booksByAuthorId.stream().map(bookMapper::toBookDto).toList();
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Update Book", description = "Update book based on book id and author id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating of Book"),
            @ApiResponse(responseCode = "404", description = "Author Not Found"),
            @ApiResponse(responseCode = "404", description = "Book Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PutMapping(path = "/{bookId}/author/{authorId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long bookId,
                                              @PathVariable long authorId,
                                              @RequestBody @Valid BookDto bookDto) {
        BookEntity bookEntity = bookMapper.toBookEntity(bookDto);
        BookEntity updatedBook = bookService.updateBook(bookId, authorId, bookEntity);
        return new ResponseEntity<>(bookMapper.toBookDto(updatedBook), HttpStatus.OK);
    }

    @Operation(summary = "Partial Update Book", description = "Partial update book based on book id and author id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Updating of Book"),
            @ApiResponse(responseCode = "404", description = "Author Not Found"),
            @ApiResponse(responseCode = "404", description = "Book Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PatchMapping(path = "/{bookId}/author/{authorId}")
    public ResponseEntity<BookDto> partialUpdate(@PathVariable long bookId,
                                                 @PathVariable long authorId,
                                                 @RequestBody Map<String, Object> bookDto) {
        BookEntity bookEntity = bookService.partialUpdate(bookId, authorId, bookDto);
        return new ResponseEntity<>(bookMapper.toBookDto(bookEntity), HttpStatus.OK);
    }

    @Operation(summary = "Delete Book By Author ID", description = "Delete a book based on an author ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of book"),
            @ApiResponse(responseCode = "404", description = "Author does not exist"),
            @ApiResponse(responseCode = "404", description = "Book does not exist")
    })
    @DeleteMapping(path = "/{bookId}/author/{authorId}")
    public ResponseEntity<HttpStatus> deleteBookByAuthorId(@PathVariable long bookId, @PathVariable long authorId) {
        bookService.deleteBookByAuthorId(bookId, authorId);
        return ResponseEntity.noContent().build();
    }

}
