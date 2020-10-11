package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RepeatedCnpjException extends RuntimeException {
    public RepeatedCnpjException(String message){
        super(message);
    }
}
