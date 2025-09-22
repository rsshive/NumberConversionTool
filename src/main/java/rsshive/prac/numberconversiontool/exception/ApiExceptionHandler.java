// ApiExceptionHandler.java
package rsshive.prac.numberconversiontool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(assignableTypes = rsshive.prac.numberconversiontool.controller.ConverterApiController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<?> handleConversion(ConversionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
