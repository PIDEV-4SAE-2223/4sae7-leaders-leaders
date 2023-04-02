package com.example.backend.Controller;

import com.example.backend.Entity.Certificat;
import com.example.backend.Entity.Formation;
import com.example.backend.Repository.CertificatRepository;
import com.example.backend.Repository.FormationRepository;
import com.example.backend.Services.CertificatService;
import com.example.backend.generic.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificat")
public class CertificatControler extends GenericController<Certificat,Long> {
    @Autowired
    private CertificatRepository certificatRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private CertificatService certificatService;

    @PostMapping("/add/formation/{idFormation}")
    @ResponseBody
    public ResponseEntity<Object> addCertification(Model model, @PathVariable Long idFormation, @RequestBody Certificat certificat) throws Exception {
        Formation formation = formationRepository.findByIdFormation(idFormation);
        if (formation == null) {
            return ResponseEntity.ok("Error: formation not found!");
        }
        try {
            Certificat getCertification = certificatRepository.save(certificat);
            certificatRepository.save(getCertification);
            formation.setId(idFormation);
            formation.setCertificat(getCertification);
            formationRepository.save(formation);
            String qrCodePath = certificatService.createQrCode(model, formation.getId());
            getCertification.setPathQrcode(qrCodePath);
            certificatRepository.save(getCertification);
            return ResponseEntity.ok(getCertification);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @GetMapping("/qrcode/{idFormation}")
    public String getQRCode(Model model, @PathVariable Long idFormation){
        return certificatService.createQrCode(model, idFormation);
    }
}