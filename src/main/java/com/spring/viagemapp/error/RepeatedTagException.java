package com.spring.viagemapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RepeatedTagException extends RuntimeException 
{
	public RepeatedTagException(String message){
        super(message);
    }
}
