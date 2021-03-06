package com.example.tutoperso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidUsernameException extends RuntimeException {
    public NotValidUsernameException() {
        super();
    }

    public NotValidUsernameException(String s) {
        super(s);
    }
}
