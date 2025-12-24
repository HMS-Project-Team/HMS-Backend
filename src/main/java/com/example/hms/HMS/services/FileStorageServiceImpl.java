package com.example.hms.HMS.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import java.time.OffsetDateTime;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.options.BlobParallelUploadOptions;

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
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
            blobClient.uploadWithResponse(new BlobParallelUploadOptions(file.getInputStream()).setHeaders(headers),
                    null, null);
            return fileName;
        } catch (Exception e) {
            throw new IOException("Failed to upload file to Azure", e);
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        // Backward compatibility: if it's already a full URL, return as is
        if (fileName.startsWith("http")) {
            return fileName;
        }

        try {
            BlobClient blobClient = containerClient.getBlobClient(fileName);

            // Define SAS permissions (Read only)
            BlobSasPermission blobSasPermission = new BlobSasPermission().setReadPermission(true);

            // Define SAS values (expiry time 1 hour)
            BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(
                    OffsetDateTime.now().plusHours(1),
                    blobSasPermission);
            sasValues.setContentDisposition("inline");

            // Generate SAS token
            String sasToken = blobClient.generateSas(sasValues);

            // Return URL with SAS token
            return blobClient.getBlobUrl() + "?" + sasToken;
        } catch (Exception e) {
            // Fallback: return the raw Blob URL if SAS generation fails (though it likely
            // won't work for private blobs)
            return containerClient.getBlobClient(fileName).getBlobUrl();
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
