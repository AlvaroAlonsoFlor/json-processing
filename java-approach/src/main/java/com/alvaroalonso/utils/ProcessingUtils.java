package com.alvaroalonso.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ProcessingUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String readFile(String path) {
        String fileAsString = "";
        try {
            log.info("Starting to read file: " + path);
            fileAsString = FileUtils.readFileToString(new File(path), "UTF-8");
            log.info("File read");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return fileAsString;
    }

    public static <T> T parseJson(String json, Class<T> type) {
        T mappedJson = null;
        try {
            log.info("Parsing json");
            mappedJson = OBJECT_MAPPER.readValue(json, type);
            log.info("Json parsed");
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return mappedJson;
    }

    public static <T> T convertJsonFileToObject(String path, Class<T> objectType) {
        return parseJson(readFile(path), objectType);
    }

}
