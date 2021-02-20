package com.alvaroalonso.utils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessingUtilsTest {

    private ListAppender<ILoggingEvent> listAppender;
    private final String START_LOG_MESSAGE = "Starting to read file";

    @BeforeEach
    public void setup() {
       ch.qos.logback.classic.Logger logger = (Logger) LoggerFactory.getLogger(ProcessingUtils.class);
       listAppender = new ListAppender<>();
       listAppender.start();
       logger.addAppender(listAppender);
    }

    @Test
    public void shouldReadFileAndReturnString() {
        final String fileString = ProcessingUtils.readFile("src/test/resources/read-file.txt");
        assertThat(fileString, equalTo("FileReaderTest"));
        assertThat(listAppender.list.get(0).getMessage(), equalTo(START_LOG_MESSAGE));
        assertThat(listAppender.list.get(1).getMessage(), equalTo("File read"));
    }

    @Test
    public void shouldLogAnErrorIfItIsNotAbleToReadFile()  {
        final String malformedPath = "malformed*%path";
        final String fileString = ProcessingUtils.readFile(malformedPath);
        assertThat(fileString, isEmptyString());
        assertThat(listAppender.list.get(0).getMessage(), equalTo(START_LOG_MESSAGE));
        assertThat(listAppender.list.get(1).getMessage(), equalTo(String.format("File '%s' does not exist", malformedPath)));
    }
}
