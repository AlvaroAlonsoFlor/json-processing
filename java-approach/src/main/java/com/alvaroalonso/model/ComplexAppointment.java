package com.alvaroalonso.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComplexAppointment {
    private Patient patient;
    private Doctor doctor;
    private String appointmentDate;
}
