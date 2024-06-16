package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.classify.EmployeeSkill;
import lombok.*;
import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Employee extends User{
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable = new HashSet<>();
}
