package br.com.ghonda.core.exceptions;

import java.io.Serial;

public class ValidationException extends DomainException {

    @Serial
    private static final long serialVersionUID = 8283887869688879490L;

    protected ValidationException(final String message) {
        super(message);
    }

    protected ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected ValidationException(final Throwable cause) {
        super(cause);
    }

}
