package nvb.dev.springadvancedproject.exception;

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
public class BookExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BookNotFoundException.class, NoBooksFoundException.class})
    public ResponseEntity<ErrorResponseModel> handleBookNotFoundException(RuntimeException ex) {
        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .title("not found")
                .detail(ex.getLocalizedMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotStorableException.class)
    public ResponseEntity<ErrorResponseModel> handleEntityNotStorableException(RuntimeException ex) {
        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .title("save error")
                .detail(ex.getLocalizedMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
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
