package com.ds3c.tcc.ApiTcc.exception;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(Long id) {
        super("The activity with id "+id+" was not found.");
    }

    public ActivityNotFoundException() {
      super("The activity was not found.");
    }
}
