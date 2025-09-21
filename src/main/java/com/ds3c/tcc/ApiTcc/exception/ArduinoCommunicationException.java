package com.ds3c.tcc.ApiTcc.exception;

public class ArduinoCommunicationException extends RuntimeException {
    public ArduinoCommunicationException(String error) {
        super("There was an error while trying to access the arduino: "+error);
    }
}
