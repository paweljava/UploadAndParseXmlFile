package com.parse;

import com.parse.repository.FileRepository;
import com.parse.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static com.parse.TestUtils.FILE_ROOT_TEST_URI;
import static com.parse.TestUtils.UPLOADED_FILE_URI;
import static com.parse.repository.FileRepository.UPLOAD_DIRECTORY_PATH;
import static java.nio.file.Files.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@SpringBootTest
class UploadFileAppUnitTest {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Test
    void should_delete_file() throws Exception {
        // given
        final var fileName = "users.xml";
        final var file = Paths.get(UPLOAD_DIRECTORY_PATH + fileName);
        if (!exists(file)) {
            createFile(file);
        }

        // when
        fileRepository.deleteFile(fileName);

        // then
        assertFalse(exists(file));
        assertThrows(NoSuchFileException.class, () -> fileRepository.deleteFile(fileName));
    }

    @Test
    void should_upload_file() throws Exception {
        // given
        final var filePath = Paths.get(UPLOADED_FILE_URI);
        final var file = new File(FILE_ROOT_TEST_URI);
        final var fileInputStream = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fileInputStream);
        deleteIfExists(filePath);

        // when
        fileRepository.uploadFile(multipartFile);

        // then
        assertTrue(exists(filePath));
        assertThrows(FileAlreadyExistsException.class, () -> fileRepository.uploadFile(multipartFile));
        deleteIfExists(filePath);
    }

    @Test
    void should_parse_file() throws Exception {
        // given
        final var file = new File(FILE_ROOT_TEST_URI);
        final var fis = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fis);

        // when
        final var result = fileService.parseXmlListToJavaList(multipartFile);

        // then
        assertEquals("Kalle Anka", result.get(0).getName());
        assertEquals("scrooge@email.dt", result.get(1).getEmail());
        assertEquals("arneanka", result.get(2).getUserName());
    }
}