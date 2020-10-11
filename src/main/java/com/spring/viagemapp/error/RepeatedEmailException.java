package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RepeatedEmailException extends RuntimeException {
    public RepeatedEmailException(String message){
        super(message);
    }
}
