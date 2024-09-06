package com.example.Blogify.Service.impl;

import com.example.Blogify.Service.Int.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // file name
        String name = file.getOriginalFilename();
        // abc.png

        // random name generate file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        // Full path
        String filePath = path + File.separator + fileName;

        // crate folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream i = new FileInputStream(fullPath);
        // do logic to return inputStream
        return i;
    }
}
