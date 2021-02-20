package com.alvaroalonso.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    private String patientId;
    private String doctorId;
    private String appointmentDate;
}
