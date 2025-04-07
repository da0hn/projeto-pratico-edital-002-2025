package br.com.ghonda.core.exceptions;

import java.io.Serial;

public class FileSystemException extends DomainException {

    @Serial
    private static final long serialVersionUID = -1408343968240311209L;

    public FileSystemException(final String message) {
        super(message);
    }

    public FileSystemException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
