package br.com.ghonda.core.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends DomainException {

    @Serial
    private static final long serialVersionUID = -7725273060902156937L;

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }

}
