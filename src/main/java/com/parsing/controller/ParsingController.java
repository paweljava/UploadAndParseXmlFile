package com.parsing.controller;

import com.parsing.exception.ParseFileExtensionException;
import com.parsing.model.XmlUser;
import com.parsing.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RestController
public
class ParsingController {

    private final FileService fileService;

    private ParsingController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/{file}")
    public ResponseEntity<List<XmlUser>> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        validateFileName(file);
        final var list = fileService.parseXml(file);
        fileService.uploadFile(file);

        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    private static void validateFileName(MultipartFile file) throws ParseFileExtensionException {
        final Optional<String> fileName = Optional.ofNullable(file.getOriginalFilename());
        fileName.filter(name -> name.endsWith(".xml")).orElseThrow(ParseFileExtensionException::new);
    }

    @DeleteMapping("delete/{file}")
    public ResponseEntity<String> deleteFile(@PathVariable String file) throws IOException {

        fileService.deleteFile(file);
        return ResponseEntity.status(ACCEPTED)
                .contentType(APPLICATION_JSON)
                .body("\"Resource deleted successfully\"");
    }
}