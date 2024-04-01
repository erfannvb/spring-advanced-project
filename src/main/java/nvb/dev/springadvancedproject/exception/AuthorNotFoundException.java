package nvb.dev.springadvancedproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.AUTHOR_NOT_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Author with id '%d' does not exist.";

    public AuthorNotFoundException() {
        super(AUTHOR_NOT_FOUND);
    }

    public AuthorNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
