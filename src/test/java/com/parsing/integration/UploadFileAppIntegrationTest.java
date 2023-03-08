package com.parsing.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parsing.model.XmlUser;
import com.parsing.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static com.parsing.TestUtils.FILE_ROOT_TEST_URI;
import static com.parsing.TestUtils.UPLOADED_FILE_URI;
import static com.parsing.repository.FileRepository.UPLOAD_DIRECTORY_PATH;
import static java.nio.file.Files.*;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
class UploadFileAppIntegrationTest {

    private final static String EMPTY_FILE_ROOT_TEST_URI =
            "src/test/java/resources/file/users_empty_file.xml";

    private final static String SYNTAX_ERROR_FILE_ROOT_TEST_URI =
            "src/test/java/resources/file/users_syntax_error.xml";

    private final static String TEXT_FILE_ROOT_TEST_URI =
            "src/test/java/resources/file/users_text_file.txt";

    private final static String NOT_XML_FILE_ROOT_TEST_URI =
            "src/test/java/resources/file/users.xm";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void should_delete_file() throws Exception {
        // given
        final var fileName = "users.xml";
        final var filePath = Paths.get(UPLOAD_DIRECTORY_PATH + fileName);
        if (!exists(filePath)) {
            createFile(filePath);
        }

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/" + fileName))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();

        //then
        assertFalse(exists(filePath));
        assertThrows(NoSuchFileException.class, () -> fileRepository.deleteFile(fileName));
    }

    @Test
    void should_upload_and_parse_file() throws Exception {
        // given
        final var filePath = Paths.get(UPLOADED_FILE_URI);
        final var file = new File(FILE_ROOT_TEST_URI);
        final var fileInputStream = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fileInputStream);
        final var mockMvc = webAppContextSetup(webApplicationContext).build();
        deleteIfExists(filePath);

        // when
        final var mvcResult = mockMvc.perform(multipart("/file")
                        .file(multipartFile)
                        .characterEncoding("utf-8")
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // then
        final var response = asList(objectMapper.readValue(mvcResult.getResponse()
                .getContentAsByteArray(), XmlUser[].class));

        assertEquals("Kalle Anka", response.get(0).getName());
        assertEquals("scrooge@email.dt", response.get(1).getEmail());
        assertEquals("arneanka", response.get(2).getUserName());
        assertTrue(exists(filePath));
        assertThrows(FileAlreadyExistsException.class, () -> fileRepository.uploadFile(multipartFile));
        deleteIfExists(filePath);
    }

    @Test
    void should_not_upload_and_parse_empty_file() throws Exception {
        // given
        final var file = new File(EMPTY_FILE_ROOT_TEST_URI);
        final var filePath = Paths.get(UPLOAD_DIRECTORY_PATH + file.getName());
        final var fileInputStream = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fileInputStream);
        final var mockMvc = webAppContextSetup(webApplicationContext).build();
        deleteIfExists(filePath);

        // when
        mockMvc.perform(multipart("/file")
                        .file(multipartFile)
                        .characterEncoding("utf-8")
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        // then
        deleteIfExists(filePath);
    }

    @Test
    void should_not_upload_and_parse_syntax_error_file() throws Exception {
        // given
        final var file = new File(SYNTAX_ERROR_FILE_ROOT_TEST_URI);
        final var filePath = Paths.get(UPLOAD_DIRECTORY_PATH + file.getName());
        final var fileInputStream = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fileInputStream);
        final var mockMvc = webAppContextSetup(webApplicationContext).build();
        deleteIfExists(filePath);

        // when
        mockMvc.perform(multipart("/file")
                        .file(multipartFile)
                        .characterEncoding("utf-8")
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        // then
        deleteIfExists(filePath);
    }

    @Test
    void should_not_upload_and_parse_text_file() throws Exception {
        // given
        final var file = new File(TEXT_FILE_ROOT_TEST_URI);
        final var filePath = Paths.get(UPLOAD_DIRECTORY_PATH + file.getName());
        final var fileInputStream = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fileInputStream);
        final var mockMvc = webAppContextSetup(webApplicationContext).build();
        deleteIfExists(filePath);

        // when
        mockMvc.perform(multipart("/file")
                        .file(multipartFile)
                        .characterEncoding("utf-8")
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        // then
        deleteIfExists(filePath);
    }

    @Test
    void should_not_upload_and_parse_not_xml_extension_file() throws Exception {
        // given
        final var file = new File(NOT_XML_FILE_ROOT_TEST_URI);
        final var filePath = Paths.get(UPLOAD_DIRECTORY_PATH + file.getName());
        final var fileInputStream = new FileInputStream(file);
        final var multipartFile = new MockMultipartFile("file", file.getName(), MULTIPART_FORM_DATA_VALUE, fileInputStream);
        final var mockMvc = webAppContextSetup(webApplicationContext).build();
        deleteIfExists(filePath);

        // when
        mockMvc.perform(multipart("/file")
                        .file(multipartFile)
                        .characterEncoding("utf-8")
                        .contentType(MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        // then
        deleteIfExists(filePath);
    }
}