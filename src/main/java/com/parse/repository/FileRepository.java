package com.parse.repository;

import com.parse.exception.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.delete;
import static java.util.Objects.requireNonNull;

@Repository
public class FileRepository {

    public static final String UPLOAD_DIRECTORY_PATH =
            "src/main/resources/upload/";

    public void uploadFile(MultipartFile file) throws Exception {
        try {
            copy(file.getInputStream(),
                    Path.of(UPLOAD_DIRECTORY_PATH).resolve(requireNonNull(file.getOriginalFilename())));
        } catch (FileAlreadyExistsException e) {
            throw new ParseFileAlreadyExistsException(e);
        } catch (IOException e) {
            throw new ParseAccessTemporaryFileException(e);
        }
    }

    public void deleteFile(String file) throws IOException {
        final var filePath = Path.of(UPLOAD_DIRECTORY_PATH).resolve(file);
        try {
            delete(filePath);
        } catch (NoSuchFileException e) {
            throw new ParseFileNotFoundException(e);
        } catch (SecurityException e) {
            throw new ParseNoPermissionToDeleteException(e);
        } catch (IOException e) {
            throw new ParseDeleteFileException(e);
        }
    }
}