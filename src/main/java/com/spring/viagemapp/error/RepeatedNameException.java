package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RepeatedNameException extends RuntimeException {
    public RepeatedNameException(String message){
        super(message);
    }
}
