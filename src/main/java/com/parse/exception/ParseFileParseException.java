package com.parse.exception;

import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseFileParseException extends Exception {

    private static final Logger logger = LoggerFactory.getLogger(ParseFileParseException.class);

    public ParseFileParseException(JAXBException e) {
        super("Could not parse the file."
                + "\n" + "\"detail message\":\"" + e.getMessage() + "\""
                + "\n" + "\"code\":\"" + e.getErrorCode() + "\""
                + "\n" + "\"cause\":\"" + e.getCause());

        logger.error("""
                        "Could not parse the file."
                         Message: {}
                         Cause: {}
                         Stacktrace: {}""",
                e.getMessage(), e.getCause(), e.getStackTrace());
    }
}