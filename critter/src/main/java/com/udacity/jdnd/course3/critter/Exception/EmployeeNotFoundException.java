package com.udacity.jdnd.course3.critter.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "NOT FOUND ANY EMPLOYEE")
public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(String msg){
        super(msg);
    }
}