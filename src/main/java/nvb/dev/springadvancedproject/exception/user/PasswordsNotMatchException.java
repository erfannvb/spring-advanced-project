package nvb.dev.springadvancedproject.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.PASSWORD_NOT_MATCH;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordsNotMatchException extends RuntimeException {

    public PasswordsNotMatchException() {
        super(PASSWORD_NOT_MATCH);
    }
}
