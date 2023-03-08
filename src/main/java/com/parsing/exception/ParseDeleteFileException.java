package com.parsing.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ParseDeleteFileException extends IOException {

    private static final Logger logger = LoggerFactory.getLogger(ParseDeleteFileException.class);

    public ParseDeleteFileException(IOException e) {
        super("Could not delete file. I/O error occurs"
                + "\n" + "\"detail message\":\"" + e.getMessage() + "\""
                + "\n" + "\"cause\":\"" + e.getCause());

        logger.error("\"Could not delete file. I/O error occurs\"\n Message: {}\n Cause: {}\n Stacktrace: {}",
                e.getMessage(), e.getCause(), e.getStackTrace());
    }
}