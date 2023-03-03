/*package com.parse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class FileParseExceptionAdvise {

    @ResponseBody
    @ExceptionHandler(FileParseException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String fileParseExceptionHandler(FileParseException e) {
        return e.getMessage();
    }
}*/
