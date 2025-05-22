package ser.mil.rideapp.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class HandleApiException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionError> handleRuntimeException(RuntimeException e) {
        String message = e.getMessage();

        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
        ExceptionError error=new ExceptionError(message, LocalDateTime.now(), httpStatus.value());
        return ResponseEntity.status(httpStatus).body(error);
    }

}
