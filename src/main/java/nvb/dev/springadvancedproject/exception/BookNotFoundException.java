package nvb.dev.springadvancedproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.BOOK_NOT_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super(BOOK_NOT_FOUND);
    }
}
