package de.hhn.rz.fop.exception;

import java.io.Serial;

public class FopRuntimeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7240229199245547295L;

    public FopRuntimeException(String message) {
        super(message);
    }

    public FopRuntimeException(Throwable throwable) {
        super(throwable);
    }

    public FopRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
