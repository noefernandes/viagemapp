
package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotFoundLoginException extends RuntimeException {
    public NotFoundLoginException(String message){
        super(message);
    }
}