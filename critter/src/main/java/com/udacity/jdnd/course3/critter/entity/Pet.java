package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udacity.jdnd.course3.critter.classify.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToOne
    private Customer customer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String notes;
}
