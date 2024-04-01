package nvb.dev.springadvancedproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.NO_AUTHORS_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoAuthorsFoundException extends RuntimeException {

    public NoAuthorsFoundException() {
        super(NO_AUTHORS_FOUND);
    }
}
