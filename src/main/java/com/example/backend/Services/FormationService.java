package com.example.backend.Services;

import com.example.backend.Entity.*;
import com.example.backend.Repository.*;
import com.example.backend.dto.ResponseFormation;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FormationService extends IGenericServiceImp<Formation, Long> implements InFormationService {

    private final UserRepository userRepository;

    private final FormationRepository formationRepository;

    private final   IImageDbService imageService;
    private final ImageDbRepository imageRepository;
    private final CertificatRepository certificatRepository;

    private final QuizzRepository quizzRepository;


    @Override
    public Formation add(Formation f)
    {
        f.setPeriod(this.calculPeriod(f));
       return formationRepository.save(f);

    }


    @Override
    public int calculPeriod(Formation formation ) {
        int period=0;
        if (formation.getStart_date()!=null  && formation.getEnd_date()!=null )
        {
            long start_date = formation.getStart_date().getTime();
            long end_date = formation.getEnd_date().getTime();

            int p = ((int) ((start_date - end_date) / (24 * 60 * 60 * 1000)));
            period = Math.abs(p);
        }
        return period;

    }


    @Override
    public ResponseEntity<Object> uploadAndAssignImageToFormation( MultipartFile image, Long idFormation) {
        Formation formation = this.retrieveById(idFormation);
        try {
            Image h = imageService.uploadImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if ( formation != null) {
            Image dbImage = imageRepository.findByName(image.getOriginalFilename());
            formation.setImg(dbImage);
        } else {
            return ResponseEntity.ok("Error: Training not found!!");
        }
        formationRepository.save(formation);
        return ResponseEntity.ok(formation);
    }



    @Override
    public ResponseEntity<Object> AssignImageToFormation(Long idImage, Long idFormation) {
        Formation formation = this.retrieveById(idFormation);
        Image image =imageService.retrieveById(idImage);
        if (formation!=null   &&  image !=null)
            formation.setImg(image);
        return ResponseEntity.ok(formation);
    }


    @Override
    public ResponseEntity<Object> AssignCertificatToFormation(Long idCertificat, Long idFormation)
    {
      Certificat c= certificatRepository.findById(idCertificat).orElseThrow(() -> new NotFoundException("Certificat Not Found!!"));
      Formation f= formationRepository.findById(idFormation).orElseThrow(() -> new NotFoundException("Training Not Found!!"));;
        if (c!=null && f!=null)
            f.setCertificat(c);
            return ResponseEntity.ok(f);

    }




    @Override
    public ResponseEntity<Object> affectFormationToParticipant(Long idFormation, Long idParticipant) {
        User participant = userRepository.findById(idParticipant).orElseThrow(() -> new NotFoundException("Id participant not found"));
        boolean isParticipant = participant.getAuthorities().iterator().next().getAuthority().equals("ROLE_USER");
        if (!isParticipant) {
            return ResponseEntity.ok("Error found: wrong id participant");
        }
        Formation formation = this.retrieveById(idFormation);

        Set<Quizz> formationQuizzs =formation.getQuizzes();
        for (Quizz q  :  formationQuizzs ) {
            Set<User> l=q.getQuizzLearnes();
            l.add(participant);
            q.setQuizzLearnes(l);
            quizzRepository.save(q);
        }

        if (formation == null) {
            return ResponseEntity.ok("Error found: formation not found");
        }
        try {
            Set<Formation> formations = participant.getFormationsparticip();
            formations.add(formation);
            participant.setFormationsparticip(formations);
            userRepository.save(participant);
            return ResponseEntity.ok(participant.getFormationsparticip());
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }






    @Override
    public ResponseEntity<Object> affectFormationToFormer( Long idFormation,Long idFormer) {
        User former = this.userRepository.findById(idFormer).orElseThrow(() -> new NotFoundException("Id former not found"));
        boolean isFormer = former.getAuthorities().iterator().next().getAuthority().equals("ROLE_FORMATEUR");
        if (!isFormer) {
            return ResponseEntity.ok("Error found: wrong id former");
        }
        Formation formation = this.retrieveById(idFormation);
        if (formation == null) {
            return ResponseEntity.ok("Error found: formation not found");
        }
        try {
//            Set<Formation> formations = former.getFormations_former();
//            formations.add(formation);
//            former.setFormations_former(formations);
//            userRepository.save(former);
//            return ResponseEntity.ok(former.getFormations_former());
            formation.setFormer(former);
            formationRepository.save(formation);
            return ResponseEntity.ok(formation);
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @Override
    public ResponseFormation getAdequateFormations(Long idprofil) {
        User account = this.userRepository.findById(idprofil).orElseThrow(() -> new NotFoundException("Id account not found"));
        String skills = account.getSkills();
        List<Formation> formations = formationRepository.searchCritireaSkills(skills);
        ResponseFormation response = new ResponseFormation(formations);
        return response;
    }

    @Override
    public List<Formation> getall() {
        return this.formationRepository.findAll();
    }


    @Override
    public ResponseFormation searchFormationsByPeriod(int period) {
        List<Formation> formations = formationRepository.searchCritireaPeriod(period);
        ResponseFormation response = new ResponseFormation(formations);
        return response;
    }


    @Override
    public ResponseEntity<Set<Formation>> searchFormationsByFormer( Long formerId) {
        User account = this.userRepository.findById(formerId).orElseThrow(() -> new NotFoundException("Id account not found"));
        boolean isParticipant = account.getAuthorities().iterator().next().getAuthority().equals("ROLE_FORMATEUR");
        if (!isParticipant) {
            throw new NotFoundException("Error found: wrong role formater!");
        }
        Set<Formation> formations = account.getFormations_former();
        return ResponseEntity.ok(formations);
    }


    @Transactional
    @Override
    public Boolean deleteFormationAndAssociatedQuizzs(Long id) {
        if (formationRepository.existsById(id)) {
            Formation formation = this.formationRepository.findById(id).orElseThrow(() -> new NotFoundException("Id formation not found"));
            Set <Quizz>  FormationQuizzs =formation.getQuizzes();

            for (Quizz q: FormationQuizzs ) {
                Quizz quizz = quizzRepository.findById(q.getId()).orElseThrow(() -> new NotFoundException("Id quizz not found"));
                quizzRepository.deleteById(quizz.getId());
                quizzRepository.flush();


            }

            for (User user : formation.getParticipants()) {
                user.getFormationsparticip().remove(formation);
            }

            //userRepository.flush();
            //get users associated to formation to delete
            //dans user table user when its formation equal to formation to delete, set its formation to null

            formationRepository.deleteById(id);


            return true;
        }
        else {return false;}
    }


}

