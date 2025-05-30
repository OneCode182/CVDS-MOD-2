package eci.cvds.mod2.util;

import eci.cvds.mod2.exceptions.ElementException;
import eci.cvds.mod2.exceptions.ElementNotFoundException;
import eci.cvds.mod2.exceptions.LoanNotFoundException;
import eci.cvds.mod2.exceptions.ReservationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<String> handleElementNotFound(ElementNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleElementNotFound(LoanNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleElementNotFound(ReservationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

