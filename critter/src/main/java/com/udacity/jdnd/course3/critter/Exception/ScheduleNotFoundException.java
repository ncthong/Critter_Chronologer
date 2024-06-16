package com.udacity.jdnd.course3.critter.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "NOT FOUND ANY SCHEDULE")
public class ScheduleNotFoundException extends RuntimeException{
    public ScheduleNotFoundException(String msg){
        super(msg);
    }
}
