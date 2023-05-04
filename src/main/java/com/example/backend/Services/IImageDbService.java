package com.example.backend.Services;

import com.example.backend.Entity.Image;
import com.example.backend.generic.IGenericService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageDbService extends IGenericService<Image,Long> {
    Image uploadImage(MultipartFile file) throws IOException ;
    Image getInfoByImageByName(String name) ;
    byte[] getImage(String name) ;
    byte[] getById(long id);

}
