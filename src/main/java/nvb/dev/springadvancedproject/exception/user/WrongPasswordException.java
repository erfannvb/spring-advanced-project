package nvb.dev.springadvancedproject.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.WRONG_PASSWORD;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super(WRONG_PASSWORD);
    }
}
