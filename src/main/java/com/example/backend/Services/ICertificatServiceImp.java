package com.example.backend.Services;


import com.example.backend.Entity.Certificat;
import com.example.backend.Repository.CertificatRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
    //@Transactional
    //@Slf4j
public class ICertificatServiceImp extends IGenericServiceImp<Certificat,Long> implements ICertificatService{

        private final CertificatRepository certificatRepository;



    }