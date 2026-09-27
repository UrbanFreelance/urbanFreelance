package com.urbanlance.common;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException{
    private HttpStatus httpStatus;
    protected BaseException(String msg,HttpStatus status){
        super(msg);
        this.httpStatus = status;
    }

    public HttpStatus getStatus(){
        return this.httpStatus;
    }
}
