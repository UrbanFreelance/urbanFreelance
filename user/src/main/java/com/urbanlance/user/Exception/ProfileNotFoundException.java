package com.urbanlance.user.Exception;

import com.urbanlance.common.BaseException;
import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends BaseException {
    public ProfileNotFoundException(String message) {
        super(message,HttpStatus.NOT_FOUND);
    }
}
