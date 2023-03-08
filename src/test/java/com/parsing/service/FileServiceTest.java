package com.parsing.service;

import com.parsing.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static com.parsing.TestUtils.FILE_ROOT_TEST_URI;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

class FileServiceTest {

    private final FileRepository fileRepository = mock(FileRepository.class);
    private final FileService fileService = new FileService(fileRepository);

    @Test
    void should_delete_file() throws Exception {
        // given
        final var fileName = "users.xml";

        // when
        fileService.deleteFile(fileName);

        // then
        verify(fileRepository).deleteFile(fileName);
    }

    @Test
    void should_upload_file() throws Exception {
        // given
          final var file = new File(FILE_ROOT_TEST_URI);
        final var fileInputStream = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fileInputStream);

        // when
        fileService.uploadFile(multipartFile);

        // then
        verify(fileRepository).uploadFile(multipartFile);
    }

    @Test
    void should_parse_file() throws Exception {
        // given
        final var file = new File(FILE_ROOT_TEST_URI);
        final var fis = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fis);

        // when
        final var result = fileService.parseXml(multipartFile);

        // then
        assertEquals("Kalle Anka", result.get(0).getName());
        assertEquals("scrooge@email.dt", result.get(1).getEmail());
        assertEquals("arneanka", result.get(2).getUserName());
    }
}