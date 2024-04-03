package nvb.dev.springadvancedproject.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class FullNameExistsException extends RuntimeException {

    private static final String ERROR_MESSAGE = "'%s' already exists.";

    public FullNameExistsException(String fullName) {
        super(ERROR_MESSAGE.formatted(fullName));
    }
}
