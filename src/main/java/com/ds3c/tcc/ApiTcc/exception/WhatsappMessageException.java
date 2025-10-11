package com.ds3c.tcc.ApiTcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WhatsappMessageException extends RuntimeException {
    public WhatsappMessageException(String error) {
        super("Error while trying to send the message: "+error);
    }
    public WhatsappMessageException() {
        super("Error while trying to send the message.");
    }
}
