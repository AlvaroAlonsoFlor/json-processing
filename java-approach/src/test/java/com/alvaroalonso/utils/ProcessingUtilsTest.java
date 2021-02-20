package com.alvaroalonso.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.alvaroalonso.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static com.alvaroalonso.utils.ProcessingUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProcessingUtilsTest {

    private ListAppender<ILoggingEvent> listAppender;
    private final String START_READ_FILE_LOG_MESSAGE = "Starting to read file";
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
        final String fileString = readFile("src/test/resources/read-file.txt");
        assertThat(fileString, equalTo("FileReaderTest"));
        assertFileReadSuccessfullyHasRightLogMessages();
    }

    @Test
    public void shouldLogAnErrorIfItIsNotAbleToReadFile() {
        final String malformedPath = "malformed*%path";
        final String fileString = readFile(malformedPath);
        assertThat(fileString, isEmptyString());
        assertFailureToReadFileLogMessages(malformedPath);
    }

    @Test
    public void shouldParseJsonSuccessfully() {
        String json = "{\n" +
                "  \"patientId\": \"u234982\",\n" +
                "  \"doctorId\": \"d981652\",\n" +
                "  \"appointmentDate\": \"2021/02/20 10:00\"\n" +
                "}";

        assertThat(parseJson(json, Appointment.class), samePropertyValuesAs(createAppointment()));
        assertThat(listAppender.list.get(0).getMessage(), equalTo(START_PARSE_JSON_LOG_MESSAGE));
        assertThat(listAppender.list.get(1).getMessage(), equalTo(JSON_PARSED_LOG_MESSAGE));

    }

    @Test
    public void shouldFailToParseJsonAndLogError() {
        String json = "{\n" +
                "  \"patisdadentId\": \"uda234982\",\n" +
                "  \"doctorIsdasdd\": \"d981652\",\n" +
                "  \"appodadintmentDate\": \"2021/02/20 10:00\"\n" +
                "}";
        assertThat(parseJson(json, Appointment.class), nullValue());
        assertThat(listAppender.list.get(0).getMessage(), equalTo(START_PARSE_JSON_LOG_MESSAGE));
        assertThat(listAppender.list.get(1).getMessage(), containsString("Unrecognized field"));
    }

    @Test
    public void shouldConvertJsonFileToObjectSuccessfully() {
        assertThat(convertJsonFileToObject("src/test/resources/json-examples/success.json", Appointment.class), samePropertyValuesAs(createAppointment()));
        assertFileReadSuccessfullyHasRightLogMessages();
        assertThat(listAppender.list.get(2).getMessage(), equalTo(START_PARSE_JSON_LOG_MESSAGE));
        assertThat(listAppender.list.get(3).getMessage(), equalTo(JSON_PARSED_LOG_MESSAGE));
    }

    @Test
    public void shouldFailToConvertJsonFileWhenFileDoesNotExist() {
        String path = "failure.json";
        assertThat(convertJsonFileToObject(path, Appointment.class), nullValue());
        assertFailureToReadFileLogMessages(path);
        assertThat(listAppender.list.get(2).getMessage(), equalTo(START_PARSE_JSON_LOG_MESSAGE));
        assertThat(listAppender.list.get(3).getMessage(), containsString("No content to map due to end-of-input"));
    }

    private Appointment createAppointment() {
        final Appointment appointment = new Appointment();
        appointment.setPatientId("u234982");
        appointment.setDoctorId("d981652");
        appointment.setAppointmentDate("2021/02/20 10:00");
        return appointment;
    }

    private void assertFileReadSuccessfullyHasRightLogMessages() {
        assertThat(listAppender.list.get(0).getMessage(), equalTo(START_READ_FILE_LOG_MESSAGE));
        assertThat(listAppender.list.get(1).getMessage(), equalTo("File read"));
    }

    private void assertFailureToReadFileLogMessages(String path) {
        assertThat(listAppender.list.get(0).getMessage(), equalTo(START_READ_FILE_LOG_MESSAGE));
        assertThat(listAppender.list.get(1).getMessage(), equalTo(String.format("File '%s' does not exist", path)));
    }
}
