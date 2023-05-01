package com.example.backend.Controller;

import com.example.backend.Entity.Certificat;
import com.example.backend.Services.ICertificatService;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificat")
@AllArgsConstructor
public class CertificatControler extends GenericController<Certificat,Long> {

    private final ICertificatService certificatService;

    @PostMapping("/AddAndAssignToFormation/{idFormation}")
    @ResponseBody
    public ResponseEntity<?> addCertification(Model model, @PathVariable Long idFormation, @RequestBody Certificat certificat)  {
        try {
            return  certificatService.addCertificationToFormation(model, idFormation,  certificat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/AssignQRCodeToCertificat/{idFormation}")
    public String getQRCode(Model model, @PathVariable Long idFormation){
        return certificatService.createQrCode(model, idFormation);
    }
}