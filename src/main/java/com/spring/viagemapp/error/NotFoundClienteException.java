package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundClienteException extends RuntimeException {
    public NotFoundClienteException(String message){
        super(message);
    }
}