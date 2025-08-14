package com.ds3c.tcc.ApiTcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AttendanceNotFoundException extends RuntimeException{
    public AttendanceNotFoundException(Long id) {
        super("The attendance with id "+id+" was not found");
    }
}
