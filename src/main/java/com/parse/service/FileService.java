package com.parse.service;

import com.parse.exception.ParseAccessTemporaryFileException;
import com.parse.exception.ParseFileParseException;
import com.parse.model.XmlRoot;
import com.parse.model.XmlUserDto;
import com.parse.repository.FileRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static jakarta.xml.bind.JAXBContext.newInstance;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void uploadFile(MultipartFile files) throws Exception {
        fileRepository.uploadFile(files);
    }

    public void deleteFile(String file) throws IOException {
        fileRepository.deleteFile(file);
    }

    public List<XmlUserDto> parseXmlListToJavaList(MultipartFile file)
            throws Exception {

        try {
            final var jaxbContext = newInstance(XmlRoot.class);
            final var unmarshaller = jaxbContext.createUnmarshaller();
            final var usersDto = (XmlRoot) unmarshaller.unmarshal(file.getInputStream());
            return usersDto.getUsers();
        } catch (JAXBException e) {
            throw new ParseFileParseException(e);
        } catch (IOException e) {
            throw new ParseAccessTemporaryFileException(e);
        }
    }
}