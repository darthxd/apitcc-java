package com.ds3c.tcc.ApiTcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AttendanceListNotFoundException extends RuntimeException{
    public AttendanceListNotFoundException(Long id) {
        super("The attendance list with id "+id+" was not found");
    }
    public AttendanceListNotFoundException() {
        super("The attendance list was not found");
    }
}
