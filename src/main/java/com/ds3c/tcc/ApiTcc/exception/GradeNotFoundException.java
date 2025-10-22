package com.ds3c.tcc.ApiTcc.exception;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException() {
        super("The requested grade was not found");
    }
}
