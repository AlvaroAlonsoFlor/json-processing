package com.alvaroalonso;

import com.alvaroalonso.model.Appointment;
import com.alvaroalonso.model.ComplexAppointment;
import lombok.extern.slf4j.Slf4j;

import static com.alvaroalonso.utils.ProcessingUtils.convertJsonFileToObject;

@Slf4j
public class TestPlayground {

    public static void main(String[] args) {
        log.info("Playground started");
        convertJsonFileToObject("src/test/resources/json-examples/success.json", Appointment.class);
        final ComplexAppointment[] list = convertJsonFileToObject("src/test/resources/json-examples/complex-example.json", ComplexAppointment[].class);
        System.out.println("Number of appointments: " + list.length);
        for (ComplexAppointment appointment : list) {
            System.out.println("Appointment date: " + appointment.getAppointmentDate());
        }
    }
}
