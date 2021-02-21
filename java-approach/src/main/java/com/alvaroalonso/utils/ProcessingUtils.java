package com.alvaroalonso.utils;

import com.alvaroalonso.exception.MapJsonToObjectException;
import com.alvaroalonso.exception.ReadFileAsStringException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ProcessingUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T convertJsonFileToObject(String path, Class<T> objectType) {
        return parseJson(readFile(path), objectType);
    }

    public static <T> T parseJson(String json, Class<T> type) {
        try {
            log.info("Parsing json");
            final T mappedObject = OBJECT_MAPPER.readValue(json, type);
            log.info("Json parsed");
            return mappedObject;
        } catch (JsonProcessingException e) {
            // TODO: discuss blocking vs non blocking
            throw new MapJsonToObjectException(String.format("Error parsing json to java object [%s]", type.getName()), e);
        }
    }

    public static String readFile(String path) {
        try {
            log.info("Starting to read file: " + path);
            String fileAsString = FileUtils.readFileToString(new File(path), "UTF-8");
            log.info("File read");
            return fileAsString;
        } catch (IOException e) {
            // TODO: discuss blocking vs non blocking
            throw new ReadFileAsStringException("Error while reading file: " + path, e);
        }
    }

}
