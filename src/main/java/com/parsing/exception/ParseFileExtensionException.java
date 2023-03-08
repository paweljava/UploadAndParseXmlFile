package com.parsing.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseFileExtensionException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ParseFileExtensionException.class);

    public ParseFileExtensionException() {
        super("File extensions not allowed.");

        logger.error("""
                        "File extensions not allowed."
                         Message: {}
                         Cause: {}
                         Stacktrace: {}""",
                getMessage(), getCause(), getStackTrace());
    }
}