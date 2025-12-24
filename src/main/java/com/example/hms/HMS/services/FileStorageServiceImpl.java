package com.example.hms.HMS.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final BlobContainerClient containerClient;

    public FileStorageServiceImpl(BlobContainerClient containerClient) {
        this.containerClient = containerClient;
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (Exception e) {
            throw new IOException("Failed to upload file to Azure", e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) throws IOException {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }
        try {
            // Extract blob name from URL if provided, otherwise assume it's the blob name
            String blobName = fileUrl.contains("/") ? fileUrl.substring(fileUrl.lastIndexOf('/') + 1) : fileUrl;
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            if (blobClient.exists()) {
                blobClient.delete();
            }
        } catch (Exception e) {
            throw new IOException("Failed to delete file from Azure", e);
        }
    }
}
