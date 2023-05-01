package com.example.backend.Services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@AllArgsConstructor

public class UploadImageService implements  IImageStorageService{

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("Upload-Images");
    private final ResourceLoader resourceLoader;

    @Override
    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }



//    @Override
//    public List<Resource> loadAll() throws IOException {
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources("classpath:Upload-Images/*");
//        return Arrays.asList(resources);
//    }

    @Override
    public List<Resource> loadAll() {
        Resource resource = resourceLoader.getResource("file:./Upload-Images/");
        Path directory = null;
        try {
            directory = resource.getFile().toPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Resource> resources = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    resources.add(new UrlResource(path.toUri()));
                    System.out.println(resources.size());
                }
            }

            System.out.println(resources.size());

            return resources;
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");

        }

    }

//    public List<Resource> loadAll() throws IOException {
//        Resource resource = resourceLoader.getResource("file:./Upload-Images/");
//        System.out.println(resource.contentLength());
//
//        Path file = resource.getFile().toPath();
//        File[] files = file.toFile().listFiles();
//        System.out.println(files.length);
//
//        List<Resource> resources = new ArrayList<>();
//        for (File f : files) {
//            if (f.isFile()){
//                System.out.println(f.getName());
//                Path pathfile=file.resolve(f.getName());
//                resources.add(new UrlResource(pathfile.toUri()));
//            }
//        }
//
//        System.out.println(resource.contentLength());
//        return resources;
//    }


    @Override
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    @Override
    public void init() {

        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

}

