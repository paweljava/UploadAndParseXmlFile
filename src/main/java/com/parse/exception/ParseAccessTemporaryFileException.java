package com.parse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ParseAccessTemporaryFileException extends IOException {

    private static final Logger logger = LoggerFactory.getLogger(ParseAccessTemporaryFileException.class);

    public ParseAccessTemporaryFileException(IOException e) {
        super("Could not process file. Access error (temporary store fails)"
                + "\n" + "or I/O error occurs when reading or writing"
                + "\n" + "\"detail message\":\"" + e.getMessage() + "\""
                + "\n" + "\"cause\":\"" + e.getCause());

        logger.error("""
                        "Could not process file. Access error (temporary store fails) or I/O error occurs when reading or writing"
                         Message: {}
                         Cause: {}
                         Stacktrace: {}""",
                e.getMessage(), e.getCause(), e.getStackTrace());
    }
}