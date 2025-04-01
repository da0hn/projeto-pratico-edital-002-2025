package br.com.ghonda.core.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
} 