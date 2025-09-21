package com.ds3c.tcc.ApiTcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BiometryReadException extends RuntimeException {
    public BiometryReadException(String error) {
        super("Error while trying to read the biometry: "+error);
    }
    public BiometryReadException() {
        super("Error while trying to read the biometry.");
    }
}
