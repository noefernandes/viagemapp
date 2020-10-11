package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RepeatedCpfException extends RuntimeException {
    public RepeatedCpfException(String message){
        super(message);
    }
}
