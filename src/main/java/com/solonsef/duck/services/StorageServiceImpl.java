package com.solonsef.duck.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;

@Service
public class StorageServiceImpl implements StorageService {

    S3Client s3Client;
    @Value("${bucket}")
    String bucket;

    StorageServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void saveImage(String filename, MultipartFile file) throws IOException {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filename)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
    }

    @Override
    public URL getImage(String filename) {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucket)
                .key(filename)
                .build();

        return s3Client.utilities().getUrl(request);
    }
}

