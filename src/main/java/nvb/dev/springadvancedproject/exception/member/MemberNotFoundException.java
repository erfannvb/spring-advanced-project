package nvb.dev.springadvancedproject.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static nvb.dev.springadvancedproject.constant.ExceptionMessage.NO_MEMBERS_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Member with id '%d' does not exist.";
    private static final String FULL_NAME_MESSAGE = "'%s' does not exist";

    public MemberNotFoundException() {
        super(NO_MEMBERS_FOUND);
    }

    public MemberNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }

    public MemberNotFoundException(String fullName) {
        super(FULL_NAME_MESSAGE.formatted(fullName));
    }
}
