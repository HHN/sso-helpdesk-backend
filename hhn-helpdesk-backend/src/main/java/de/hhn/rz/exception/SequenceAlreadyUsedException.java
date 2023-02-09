package de.hhn.rz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.GONE)
public class SequenceAlreadyUsedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1043692361268070928L;

    public SequenceAlreadyUsedException(String msg) {
        super(msg);
    }
}
