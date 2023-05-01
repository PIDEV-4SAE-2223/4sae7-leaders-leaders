package com.example.backend.Services;

import com.example.backend.Entity.Formation;
import com.example.backend.Entity.Image;
import com.example.backend.dto.FormationDTO;
import com.example.backend.dto.ResponseFormation;
import com.example.backend.generic.IGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface InFormationService extends IGenericService<Formation, Long> {
   ResponseEntity<Object> uploadAndAssignImageToFormation( MultipartFile image, Long idFormation) throws IOException ;

    //  ResponseEntity<Object> addFormation ( Formation formation, MultipartFile imageFile) ;
    ResponseEntity<Object> AssignImageToFormation( Long idImage,Long idFormation) ;
    //FormationDTO getFormationByIdWithImage(Long idFormation) ;

    ResponseEntity<Object> affectFormationToParticipant(Long idFormation,Long idParticipant);
    ResponseEntity<Object> affectFormationToFormer(Long idFormation,Long idFormer);

    ResponseFormation searchFormationsByPeriod(int period);
    ResponseEntity<Set<Formation>> searchFormationsByFormer(Long formerId);
    ResponseFormation getAdequateFormations(Long idprofil) ;
    List<Formation> getall() ;


    ResponseEntity<Object> AssignCertificatToFormation(Long idCertificat, Long idFormation);

   int calculPeriod(Formation formation ) ;

 }

