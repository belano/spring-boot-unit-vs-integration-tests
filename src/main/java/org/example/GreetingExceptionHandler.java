package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Map exceptions to HTTP codes
 */
@RestControllerAdvice
public class GreetingExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingExceptionHandler.class);

    @ExceptionHandler(GreetingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleGreetingException(GreetingException ex, WebRequest request) {
        LOGGER.error("Handling greeting exception", ex);
    }
}
