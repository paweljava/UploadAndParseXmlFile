/*package com.parse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class TransferFileExceptionAdvise {

    @ResponseBody
    @ExceptionHandler(TransferFileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String transferFileExceptionHandler(TransferFileException e) {
        return e.getMessage();
    }
}*/
