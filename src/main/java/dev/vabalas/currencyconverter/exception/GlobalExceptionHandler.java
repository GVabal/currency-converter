package dev.vabalas.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleExchangeRateNotFoundException(ExchangeRateNotFoundException exception) {
        return generateResponseBody(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FailedToReadCsvDataException.class)
    public ResponseEntity<Map<String, String>> handleFailedToReadCsvDataException(FailedToReadCsvDataException exception) {
        return generateResponseBody(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> generateResponseBody(String message, HttpStatus status) {
        Map<String, String> map = new HashMap<>();
        map.put("code", Integer.toString(status.value()));
        map.put("error", status.getReasonPhrase());
        map.put("message", message);
        map.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(map, status);
    }
}
