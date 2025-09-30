package com.ds3c.tcc.ApiTcc.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PresenceLogNotFoundException extends RuntimeException {
    public PresenceLogNotFoundException(Long studentId, LocalDate date) {
        super("The presence log for the student with ID "+studentId+" and date "
                +date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" was not found.");
    }
    public PresenceLogNotFoundException() {
        super("The presence log was not found.");
    }
}
