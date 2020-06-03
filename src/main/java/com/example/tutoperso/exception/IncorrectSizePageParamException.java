package com.example.tutoperso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectSizePageParamException extends RuntimeException {

    public IncorrectSizePageParamException() {
        super();
    }
    public IncorrectSizePageParamException(String message) {
        super(message);
    }
}
