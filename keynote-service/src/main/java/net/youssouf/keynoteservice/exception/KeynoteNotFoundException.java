package net.youssouf.keynoteservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KeynoteNotFoundException extends RuntimeException {
    public KeynoteNotFoundException(String message) {
        super(message);
    }
}
