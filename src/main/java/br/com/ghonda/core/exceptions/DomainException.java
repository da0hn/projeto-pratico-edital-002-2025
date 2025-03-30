package br.com.ghonda.core.exceptions;

import java.io.Serial;

public abstract class DomainException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 9027478506942130797L;

    protected DomainException(final String message) {
        super(message);
    }

    protected DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected DomainException(final Throwable cause) {
        super(cause);
    }

}
