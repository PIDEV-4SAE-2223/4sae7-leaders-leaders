package com.example.backend.Services;


import com.example.backend.Entity.Certificat;
import com.example.backend.generic.IGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICertificatService extends IGenericService<Certificat,Long> {
    ResponseEntity<?> addCertificationToFormation(Model model, Long idFormation, Certificat certificat) throws Exception;
     String createQrCode(Model model, Long idFormation) ;

}