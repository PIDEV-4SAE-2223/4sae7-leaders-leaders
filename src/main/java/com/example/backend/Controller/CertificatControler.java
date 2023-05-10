package com.example.backend.Controller;

import com.example.backend.Entity.Certificat;
import com.example.backend.Entity.Formation;
import com.example.backend.Services.ICertificatService;
import com.example.backend.Services.IImageDbService;
import com.example.backend.Services.InFormationService;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api/certificat" , produces= MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CertificatControler extends GenericController<Certificat,Long> {

    private final ICertificatService certificatService;
    private final IImageDbService imageDataService;

    @PostMapping("/AddAndAssignToFormation/{idFormation}")
    @ResponseBody
    public ResponseEntity<byte[]> addCertification(Model model, @PathVariable Long idFormation, @RequestBody Certificat certificat)  {
        try {
             certificatService.addCertificationToFormation(model, idFormation,  certificat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        byte[] image = imageDataService.getById((certificat.getImg()).getId());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);


    }

    @GetMapping("/AssignQRCodeToCertificat/{idFormation}")
    public String getQRCode(Model model, @PathVariable Long idFormation){
        return certificatService.createQrCode(model, idFormation);
    }




    @GetMapping(value = "/getImageById/{idC}")
    public ResponseEntity<?> getImageById(@PathVariable Long idC) {
        Certificat f=certificatService.retrieveById(idC);

        if (f.getImg()!=null) {
            long id =f.getImg().getId();
            System.out.println(id);
            byte[] image = imageDataService.getById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(image);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found for certification ID: " + idC);
    }

    @GetMapping("/getById/{id}")
    public Certificat getById(@PathVariable Long id ) {
        return	certificatService.retrieveById(id);
    }


}