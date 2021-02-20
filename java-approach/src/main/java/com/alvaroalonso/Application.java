package com.alvaroalonso;

import com.alvaroalonso.utils.ProcessingUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String args[]) {
        log.info("Application Started");
        log.info("Reading test file content: " + ProcessingUtils.readFile("src/test/resources/read-file.txt"));
        ProcessingUtils.readFile("malformed%^&");
    }
}
