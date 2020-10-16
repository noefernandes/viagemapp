
package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotFoundViagensException extends RuntimeException {
    public NotFoundViagensException(String message){
        super(message);
    }
}