package com.example.backend.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IImageStorageService {
    public List<Resource> loadAll()throws  Exception;
     void store(MultipartFile file) ;
     Resource loadFile(String filename) ;
    void deleteAll() ;
    void init() ;
}
