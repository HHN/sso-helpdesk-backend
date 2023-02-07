package de.hhn.rz.exception;

import java.io.Serial;

public class InvalidSearchException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 679948405675033721L;

    public InvalidSearchException(String msg) {
        super(msg);
    }
}
