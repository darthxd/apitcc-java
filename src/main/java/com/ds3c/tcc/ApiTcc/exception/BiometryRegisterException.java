package com.ds3c.tcc.ApiTcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BiometryRegisterException extends RuntimeException {
    public BiometryRegisterException(String error) {
        super("Error while trying to register the biometry: "+error);
    }
    public BiometryRegisterException() {
        super("Error while trying to register the biometry.");
    }
}
