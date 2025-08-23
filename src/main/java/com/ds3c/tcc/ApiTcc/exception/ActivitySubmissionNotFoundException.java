package com.ds3c.tcc.ApiTcc.exception;

public class ActivitySubmissionNotFoundException extends RuntimeException {
    public ActivitySubmissionNotFoundException(Long id) {
        super("The activity submission with id "+id+" was not found.");
    }

    public ActivitySubmissionNotFoundException() {
      super("The activity submission was not found.");
    }
}
