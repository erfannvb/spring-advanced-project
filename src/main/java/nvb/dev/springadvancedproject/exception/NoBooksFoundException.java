package nvb.dev.springadvancedproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.NO_BOOKS_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoBooksFoundException extends RuntimeException{

    public NoBooksFoundException() {
        super(NO_BOOKS_FOUND);
    }
}
