package com.parse.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ControllerAdvice
class ExceptionAdvise {

    @ResponseBody
    @ExceptionHandler(ParseAccessTemporaryFileException.class)
    private ResponseEntity<String> accessTemporaryFileExceptionHandler(ParseAccessTemporaryFileException e) {
        return ResponseEntity.unprocessableEntity()
                .contentType(APPLICATION_JSON)
                .body("\"message\":\"" + e.getMessage() + "\"");
    }

    @ResponseBody
    @ExceptionHandler(ParseDeleteFileException.class)
    private ResponseEntity<String> deleteFileExceptionHandler(ParseDeleteFileException e) {
        return ResponseEntity.status(FORBIDDEN)
                .contentType(APPLICATION_JSON)
                .body("\"message\":\"" + e.getMessage() + "\"");
    }

    @ResponseBody
    @ExceptionHandler(ParseFileAlreadyExistsException.class)
    private ResponseEntity<String> fileAlreadyExistsExceptionHandler(ParseFileAlreadyExistsException e) {
        return ResponseEntity.unprocessableEntity()
                .contentType(APPLICATION_JSON)
                .body("\"message\":\"" + e.getMessage() + "\"");
    }

    @ResponseBody
    @ExceptionHandler(ParseFileExtensionException.class)
    private ResponseEntity<String> fileExtensionExceptionHandler(ParseFileExtensionException e) {
        return ResponseEntity.unprocessableEntity()
                .contentType(APPLICATION_JSON)
                .body("\"message\":\"" + e.getMessage() + "\"");
    }

    @ResponseBody
    @ExceptionHandler(ParseFileNotFoundException.class)
    private ResponseEntity<String> fileNotFoundExceptionHandler(ParseFileNotFoundException e) {
        return ResponseEntity.unprocessableEntity()
                .contentType(APPLICATION_JSON)
                .body("\"message\":\"" + e.getMessage() + "\"");
    }

    @ResponseBody
    @ExceptionHandler(ParseFileParseException.class)
    private ResponseEntity<String> fileParseExceptionHandler(ParseFileParseException e) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .contentType(APPLICATION_JSON)
                .body("\"message\":\"" + e.getMessage() + "\"");
    }

    @ResponseBody
    @ExceptionHandler(ParseNoPermissionToDeleteException.class)
    private ResponseEntity<String> noPermissionToDeleteExceptionHandler(ParseNoPermissionToDeleteException e) {
        return ResponseEntity.status(FORBIDDEN)
                .contentType(APPLICATION_JSON)
                .body("\"message\":\"" + e.getMessage() + "\"");
    }
}