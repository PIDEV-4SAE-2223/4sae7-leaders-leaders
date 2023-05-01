package com.example.backend.Services;

import com.example.backend.Entity.Image;
import com.example.backend.Repository.ImageDbRepository;
import com.example.backend.Util.ImageUtil;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageDbService extends IGenericServiceImp<Image, Long> implements IImageDbService{

    private final ImageDbRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile file)  {

        Image x= null;
        try {
            x = Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes())).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("***********"+file.getOriginalFilename());
        System.out.println("***********"+file.getContentType());

        return imageRepository.save(x);


    }

    @Override
    @Transactional
    public Image getInfoByImageByName(String name) {
        Image dbImage = imageRepository.findByName(name);

        return Image.builder()
                .name(dbImage.getName())
                .type(dbImage.getType())
                .imageData(ImageUtil.decompressImage(dbImage.getImageData())).build();

    }

    @Override
    @Transactional
    public byte[] getImage(String name) {
        Image dbImage = imageRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.getImageData());
        return image;
    }


}