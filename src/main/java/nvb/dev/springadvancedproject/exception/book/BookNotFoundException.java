package nvb.dev.springadvancedproject.exception.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.NO_BOOKS_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Book with id '%d' does not exist.";

    public BookNotFoundException() {
        super(NO_BOOKS_FOUND);
    }

    public BookNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
