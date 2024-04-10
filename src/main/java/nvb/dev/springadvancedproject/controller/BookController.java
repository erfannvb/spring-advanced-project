package nvb.dev.springadvancedproject.controller;

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
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping(path = "/save/{authorId}")
    public ResponseEntity<BookDto> saveBook(@PathVariable long authorId, @RequestBody @Valid BookDto bookDto) {
        BookEntity bookEntity = bookMapper.toBookEntity(bookDto);
        BookEntity savedBook = bookService.saveBook(authorId, bookEntity);
        return new ResponseEntity<>(bookMapper.toBookDto(savedBook), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long bookId) {
        Optional<BookEntity> bookById = bookService.getBookById(bookId);
        return bookById.map(bookEntity -> {
            BookDto bookDto = bookMapper.toBookDto(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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

    @GetMapping(path = "/author/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthorId(@PathVariable long authorId) {
        List<BookEntity> booksByAuthorId = bookService.getBooksByAuthorId(authorId);
        List<BookDto> bookDtoList = booksByAuthorId.stream().map(bookMapper::toBookDto).toList();
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/{bookId}/author/{authorId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long bookId,
                                              @PathVariable long authorId,
                                              @RequestBody @Valid BookDto bookDto) {
        BookEntity bookEntity = bookMapper.toBookEntity(bookDto);
        BookEntity updatedBook = bookService.updateBook(bookId, authorId, bookEntity);
        return new ResponseEntity<>(bookMapper.toBookDto(updatedBook), HttpStatus.OK);
    }

    @PatchMapping(path = "/{bookId}/author/{authorId}")
    public ResponseEntity<BookDto> partialUpdate(@PathVariable long bookId,
                                                 @PathVariable long authorId,
                                                 @RequestBody Map<String, Object> bookDto) {
        BookEntity bookEntity = bookService.partialUpdate(bookId, authorId, bookDto);
        return new ResponseEntity<>(bookMapper.toBookDto(bookEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{bookId}/author/{authorId}")
    public ResponseEntity<HttpStatus> deleteBookByAuthorId(@PathVariable long bookId, @PathVariable long authorId) {
        bookService.deleteBookByAuthorId(bookId, authorId);
        return ResponseEntity.noContent().build();
    }

}
