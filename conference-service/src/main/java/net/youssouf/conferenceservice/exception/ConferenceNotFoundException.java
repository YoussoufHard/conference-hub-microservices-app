package net.youssouf.conferenceservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConferenceNotFoundException extends RuntimeException {
    
    public ConferenceNotFoundException(String message) {
        super(message);
    }
    
    public ConferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
