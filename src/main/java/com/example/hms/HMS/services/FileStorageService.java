package com.example.hms.HMS.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file) throws IOException;

    String getFileUrl(String fileName);

    void deleteFile(String fileName) throws IOException;
}
