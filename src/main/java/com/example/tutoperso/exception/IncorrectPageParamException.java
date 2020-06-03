package com.example.tutoperso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectPageParamException extends RuntimeException {

    public IncorrectPageParamException() {
        super();
    }
    public IncorrectPageParamException(String message) {
        super(message);
    }
}
