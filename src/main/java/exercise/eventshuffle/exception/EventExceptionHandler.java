package exercise.eventshuffle.exception;

import exercise.eventshuffle.dto.error.CommonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EventExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CommonErrorResponse> handleException(NotFoundException exc) {
        CommonErrorResponse error = new CommonErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CommonErrorResponse> handleException(HttpMessageNotReadableException exc) {
        CommonErrorResponse error = new CommonErrorResponse(HttpStatus.BAD_REQUEST,
                "There was an issue with processing the JSON: " + exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CommonErrorResponse> handleException(Exception exception) {
        CommonErrorResponse error = new CommonErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error: " + exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}