package com.alvaroalonso;

import com.alvaroalonso.model.Appointment;
import com.alvaroalonso.model.ComplexAppointment;
import com.alvaroalonso.utils.ProcessingUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.alvaroalonso.utils.ProcessingUtils.convertJsonFileToObject;

@Slf4j
public class Application {

    public static void main(String[] args) throws JsonProcessingException {
        log.info("Application Started");
        convertJsonFileToObject("src/main/resources/example.json", Appointment.class);

        final ComplexAppointment[] list = convertJsonFileToObject("src/main/resources/complex-example.json", ComplexAppointment[].class);
        System.out.println("Number of appointments: " + list.length);

    }
}
