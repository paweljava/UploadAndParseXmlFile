package com.parsing.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.NoSuchFileException;

public class ParseFileNotFoundException extends NoSuchFileException {

    private static final Logger logger = LoggerFactory.getLogger(ParseFileNotFoundException.class);

    public ParseFileNotFoundException(NoSuchFileException e) {
        super("File not found."
                + "\n" + "\"detail message\":\"" + e.getMessage() + "\""
                + "\n" + "\"cause\":\"" + e.getCause());

        logger.error("""
                        "File not found."
                         Message: {}
                         Cause: {}
                         Reason: {}
                         Stacktrace: {}""",
                e.getMessage(), e.getCause(), e.getReason(), e.getStackTrace());
    }
}