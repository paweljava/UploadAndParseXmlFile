package com.parse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseNoPermissionToDeleteException extends SecurityException {

    private static final Logger logger = LoggerFactory.getLogger(ParseNoPermissionToDeleteException.class);

    public ParseNoPermissionToDeleteException(SecurityException e) {
        super("No permission to delete file."
                + "\n" + "\"detail message\":\"" + e.getMessage() + "\""
                + "\n" + "\"cause\":\"" + e.getCause());

        logger.error("""
                        "No permission to delete file."
                         Message: {}
                         Cause: {}
                         Stacktrace: {}""",
                e.getMessage(), e.getCause(), e.getStackTrace());
    }
}