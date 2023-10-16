package com.solonsef.duck.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

public interface StorageService {

    void saveImage(String filename, MultipartFile file) throws IOException;

    public URL getImage(String filename) throws Exception;


}
