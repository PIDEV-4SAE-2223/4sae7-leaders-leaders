package com.example.backend.Controller;


import com.example.backend.Entity.Certificat;
import com.example.backend.Services.ICertificatService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certificat")

public class CertificatControler extends GenericController<Certificat,Long> {

    private final ICertificatService iService;


}