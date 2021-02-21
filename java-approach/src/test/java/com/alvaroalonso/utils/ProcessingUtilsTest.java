package com.alvaroalonso.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.alvaroalonso.exception.MapJsonToObjectException;
import com.alvaroalonso.exception.ReadFileAsStringException;
import com.alvaroalonso.model.Appointment;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.alvaroalonso.utils.ProcessingUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessingUtilsTest {

    private ListAppender<ILoggingEvent> listAppender;
    private final String START_PARSE_JSON_LOG_MESSAGE = "Parsing json";
    private final String JSON_PARSED_LOG_MESSAGE = "Json parsed";

    @BeforeEach
    public void setup() {
        ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(ProcessingUtils.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    public void shouldReadFileAndReturnString() {
        final String path = "src/test/resources/read-file.txt";
        final String fileString = readFile(path);
        assertThat(fileString, equalTo("FileReaderTest"));
        assertFileReadSuccessfullyHasRightLogMessages(path);
    }

    @Test
    public void shouldLogAnErrorIfItIsNotAbleToReadFile() {
        final String malformedPath = "malformed*%path";
        assertThrows(ReadFileAsStringException.class, () -> readFile(malformedPath));
    }

    @Test
    public void shouldParseJsonSuccessfully() throws IOException {
        String json = FileUtils.readFileToString(new File("src/test/resources/json-examples/success.json"), "UTF-8");
        assertThat(parseJson(json, Appointment.class), samePropertyValuesAs(createAppointment()));
        assertThat(listAppender.list.get(0).getMessage(), equalTo(START_PARSE_JSON_LOG_MESSAGE));
        assertThat(listAppender.list.get(1).getMessage(), equalTo(JSON_PARSED_LOG_MESSAGE));

    }

    @Test
    public void shouldFailToParseJsonAndLogError() throws IOException {
        String json = FileUtils.readFileToString(new File("src/test/resources/json-examples/wrong-keys.json"), "UTF-8");
        assertThrows(MapJsonToObjectException.class, () -> parseJson(json, Appointment.class));
    }

    @Test
    public void shouldConvertJsonFileToObjectSuccessfully() {
        final String path = "src/test/resources/json-examples/success.json";
        assertThat(convertJsonFileToObject(path, Appointment.class), samePropertyValuesAs(createAppointment()));
        assertFileReadSuccessfullyHasRightLogMessages(path);
        assertThat(listAppender.list.get(2).getMessage(), equalTo(START_PARSE_JSON_LOG_MESSAGE));
        assertThat(listAppender.list.get(3).getMessage(), equalTo(JSON_PARSED_LOG_MESSAGE));
    }

    @Test
    public void shouldFailToConvertJsonFileWhenFileDoesNotExist() {
        String path = "failure.json";
        assertThrows(ReadFileAsStringException.class, () -> convertJsonFileToObject(path, Appointment.class));
    }

    private Appointment createAppointment() {
        final Appointment appointment = new Appointment();
        appointment.setPatientId("u234982");
        appointment.setDoctorId("d981652");
        appointment.setAppointmentDate("2021/02/20 10:00");
        return appointment;
    }

    private void assertFileReadSuccessfullyHasRightLogMessages(String path) {
        assertThat(listAppender.list.get(0).getMessage(), equalTo("Starting to read file: " + path));
        assertThat(listAppender.list.get(1).getMessage(), equalTo("File read"));
    }

}
