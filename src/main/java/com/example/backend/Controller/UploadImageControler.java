package com.example.backend.Controller;

import com.example.backend.Services.IImageStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/images")
public class UploadImageControler {

    private final   IImageStorageService sv;



    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = "text/plain")
    public ResponseEntity<String> handleFileUpload(@RequestParam("imagefile") MultipartFile imagefile) {
        List<String> files = new ArrayList<String>();
        String message = "";
        try {
            sv.store(imagefile);
            files.add(imagefile.getOriginalFilename());

            message = "You successfully uploaded " + imagefile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + imagefile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


//    @GetMapping("/getAll")
//    public ResponseEntity<List<Resource>> getListImagesFiles(Model model)throws Exception {
//        List<Resource> files = sv.loadAll().stream()
//                .map(path -> sv.loadFile(path.getFilename()))
//                .filter(file -> file.getFilename().matches(".*\\.(png|jpg|jpeg|gif)$"))
//                .collect(Collectors.toList());
//
//        List<String> fileNames = files.stream()
//                .map(file -> file.getFilename())
//                .collect(Collectors.toList());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(files);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNames + "\"")
//                .body(files);
//    }

//    @GetMapping("/getallfiles")
//    public ResponseEntity<List<Resource>> getListFiles(Model model) {
//        List<Resource> files = null;
//        try {
//            files = sv.loadAll();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        List<Resource> fileNames = new ArrayList<>();
//        for (Resource fileName : files) {
//            UriComponents getImageFile = MvcUriComponentsBuilder
//                    .fromMethodName(UploadImageControler.class, "getImageFile", fileName.getFilename()).build();
//            fileNames.add(new FileSystemResource( getImageFile.getPath()));
//        }
//
//        return ResponseEntity.ok().body(fileNames);
//    }


    @GetMapping("/getImage/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getImageFile(@PathVariable String filename) {

        Resource file = sv.loadFile(filename);

        String contentType = URLConnection.guessContentTypeFromName(filename);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}