package com.urbanlance.job.exception;

import com.urbanlance.common.BaseException;
import org.springframework.http.HttpStatus;

public class JobNotFoundException extends BaseException {
    public JobNotFoundException(String msg, HttpStatus status) {
        super(msg, status);
    }
}
