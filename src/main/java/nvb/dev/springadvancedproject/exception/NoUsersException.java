package nvb.dev.springadvancedproject.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.NO_USERS_FOUND;

@ResponseStatus
public class NoUsersException extends RuntimeException{

    public NoUsersException() {
        super(NO_USERS_FOUND);
    }
}
