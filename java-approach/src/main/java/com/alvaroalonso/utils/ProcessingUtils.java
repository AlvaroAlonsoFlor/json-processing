package com.alvaroalonso.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ProcessingUtils {

    public static String readFile(String path) {
        String fileAsString = "";
        try {
            log.info("Starting to read file");
            fileAsString = FileUtils.readFileToString(new File(path), "UTF-8");
            log.info("File read");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return fileAsString;
    }


}
