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

@RestController
@AllArgsConstructor
@RequestMapping(value="/imageDB",produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageDbControler {


    private final IImageDbService imageDataService;

    @PostMapping(value="/uploadImage" ,consumes =  "multipart/form-data")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        Image response = imageDataService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
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
////// in Angular
//    import { Component } from '@angular/core';
//import { HttpClient } from '@angular/common/http';
//
//    @Component({
//            selector: 'app-image',
//            template: '<img [src]="imageUrl" alt="Image">',
//            })
//    export class ImageComponent {
//        imageUrl: string;
//
//        constructor(private http: HttpClient) {}
//
//        ngOnInit() {
//    const imageId = 1; // Replace with the ID of the image you want to display
//            this.http.get(`http://localhost:8080/images/${imageId}`, { responseType: 'blob' })
//      .subscribe((response: Blob) => {
//        const reader = new FileReader();
//                reader.readAsDataURL(response);
//                reader.onloadend = () => {
//          const base64data = reader.result.toString().split(',')[1];
//                    this.imageUrl = `data:image/jpeg;base64,${base64data}`;
//                };
//            });
//        }
//    }

}