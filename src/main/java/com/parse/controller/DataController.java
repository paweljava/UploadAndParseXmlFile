package com.parse.controller;

import com.parse.exception.ParseFileExtensionException;
import com.parse.model.XmlUserDto;
import com.parse.service.FileService;
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
class DataController {

    private final FileService fileService;

    DataController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/{file}")
    public ResponseEntity<List<XmlUserDto>> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        final Optional<String> fileName = Optional.ofNullable(file.getOriginalFilename());
        fileName.filter(name -> name.endsWith(".xml")).orElseThrow(ParseFileExtensionException::new);

        final var list = fileService.parseXmlListToJavaList(file);
        fileService.uploadFile(file);

        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{file}")
    public ResponseEntity<String> deleteFile(@PathVariable String file) throws IOException {

        fileService.deleteFile(file);
        return ResponseEntity.status(ACCEPTED)
                .contentType(APPLICATION_JSON)
                .body("\"Resource deleted successfully\"");
    }
}