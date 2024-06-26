package nvb.dev.springadvancedproject.exception.user;

import nvb.dev.springadvancedproject.exception.ErrorResponseModel;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({WrongPasswordException.class, PasswordsNotMatchException.class})
    public ResponseEntity<ErrorResponseModel> handleChangePasswordExceptions(RuntimeException ex) {
        ErrorResponseModel errorResponseModel = null;

        if (ex instanceof WrongPasswordException) {
            errorResponseModel = ErrorResponseModel.builder()
                    .title("Wrong Password")
                    .detail(ex.getLocalizedMessage())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();
        } else if (ex instanceof PasswordsNotMatchException) {
            errorResponseModel = ErrorResponseModel.builder()
                    .title("Passwords Not Match")
                    .detail(ex.getLocalizedMessage())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorResponseModel> handleUsernameExistsException(RuntimeException ex) {
        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .title("username exists")
                .detail(ex.getLocalizedMessage())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseModel, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleErrorNotFoundException(RuntimeException ex) {
        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .title("not found")
                .detail(ex.getLocalizedMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseModel, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {

        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .title("validation error")
                .details(getErrorList(ex))
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorList(@NonNull MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
