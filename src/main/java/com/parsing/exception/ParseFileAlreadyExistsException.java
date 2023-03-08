package com.parsing.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileAlreadyExistsException;

public class ParseFileAlreadyExistsException extends FileAlreadyExistsException {

    private static final Logger logger = LoggerFactory.getLogger(ParseFileAlreadyExistsException.class);

    public ParseFileAlreadyExistsException(FileAlreadyExistsException e) {
        super("File already exists.!"
                + "\n" + "\"detail message\":\"" + e.getMessage() + "\""
                + "\n" + "\"cause\":\"" + e.getCause());

        logger.error("""
                        File already exists.!
                         Message: {}
                         Cause: {}
                         Stacktrace: {}""",
                e.getMessage(), e.getCause(), e.getStackTrace());
    }
}