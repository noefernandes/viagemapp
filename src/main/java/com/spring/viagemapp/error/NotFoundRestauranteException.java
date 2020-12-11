package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundRestauranteException extends RuntimeException {
    public NotFoundRestauranteException(String message){
        super(message);
    }
}