package dev.vabalas.currencyconverter.exception;

public class FailedToReadCsvDataException extends RuntimeException {
    public FailedToReadCsvDataException(String message) {
        super(message);
    }
}
