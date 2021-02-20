package com.alvaroalonso;

import com.alvaroalonso.model.Appointment;
import com.alvaroalonso.utils.ProcessingUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import static com.alvaroalonso.utils.ProcessingUtils.convertJsonFileToObject;

@Slf4j
public class Application {

    public static void main(String args[]) throws JsonProcessingException {
        log.info("Application Started");
        convertJsonFileToObject("src/main/resources/example.json", Appointment.class);

    }
}
