package com.udacity.jdnd.course3.critter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udacity.jdnd.course3.critter.classify.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
@Getter @Setter
public class EmployeeRequestDTO {
    private Set<EmployeeSkill> skills;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

}
