package com.ds3c.tcc.ApiTcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SchoolUnitNotFoundException extends RuntimeException {
    public SchoolUnitNotFoundException(Long id) {
        super("The school unit with id "+id+" was not found");
    }
    public SchoolUnitNotFoundException() {
        super("The requested school unit was not found");
    }
}
