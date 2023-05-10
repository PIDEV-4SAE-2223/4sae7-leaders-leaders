package com.example.backend.Controller;

import com.example.backend.Entity.Image;
import com.example.backend.Services.IImageDbService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@CrossOrigin(origins = "*")

@RestController
@AllArgsConstructor
@RequestMapping(value="/imageDB",produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageDbControler {


    private final IImageDbService imageDataService;

    @PostMapping(value="/uploadImage" ,consumes =  "multipart/form-data")
    public ResponseEntity<byte[]> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        Image response = imageDataService.uploadImage(file);
        byte[] image = imageDataService.getById(response.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    @GetMapping("/getImageInfoByName/{name}")
    public ResponseEntity<?>  getImageInfoByName(@PathVariable("name") String name){
        Image image = imageDataService.getInfoByImageByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .body(image);
    }

    @GetMapping("/getImageByName/{name}")
    public ResponseEntity<?>  getImageByName(@PathVariable("name") String name){
        byte[] image = imageDataService.getImage(name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }


    @GetMapping(value="/getImageById/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        byte[] image = imageDataService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }



}