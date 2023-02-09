package de.hhn.rz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSearchException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 679948405675033721L;

    public InvalidSearchException(String msg) {
        super(msg);
    }
}
