package nvb.dev.springadvancedproject.exception.author;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.NO_AUTHORS_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Author with id '%d' does not exist.";

    public AuthorNotFoundException() {
        super(NO_AUTHORS_FOUND);
    }

    public AuthorNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
