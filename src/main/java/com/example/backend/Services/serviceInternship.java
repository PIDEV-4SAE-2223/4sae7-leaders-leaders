package com.example.backend.Services;

import com.example.backend.Entity.Departement;
import com.example.backend.Entity.Intern;
import com.example.backend.Entity.InternshipRequest;
import com.example.backend.Entity.Status;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.internshipRequestRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class serviceInternship implements InternshipService {

    private final internshipRequestRepo internshipRepo;
    private final internRepo internrepo;
    @Override
    public ResponseEntity<?> create(InternshipRequest saved) {//startdate must be before end date and after now
        try{
            LocalDate now =  LocalDate.now();//now Type localdate
            Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
            long diff = saved.getDesiredEndDate().getTime() - saved.getDesiredStartDate().getTime();//difference between two dates
            long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//convert result in days
            if(saved.getDesiredStartDate().after(saved.getDesiredEndDate())){
                return ResponseEntity.badRequest().body("Start Date could not be after End Date!!!!");
            }
            if (saved.getDesiredStartDate().before(datt)) {
                return ResponseEntity.badRequest().body("i think manouba is better for you!!!");
            }
            if (diffInDays < 30) {
                return ResponseEntity.badRequest().body("our internship programs must be at least one month !!");
            }
            saved.setCreatedat(datt);
            saved.setStatus(Status.ENATTENTE);
          internshipRepo.save(saved);
            return  ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving internship");
        }
    }

    @Override
    public ResponseEntity<?> update(InternshipRequest saved, Long id) {
        try{
            LocalDate now =  LocalDate.now();//now Type localdate
            Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
            long diff = saved.getDesiredEndDate().getTime() - saved.getDesiredStartDate().getTime();//difference between two dates
            long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//convert result in days
            if(saved.getDesiredStartDate().after(saved.getDesiredEndDate())){
                return ResponseEntity.badRequest().body("Start Date could not be after End Date!!!!");
            }
            if (saved.getDesiredStartDate().before(datt)) {
                return ResponseEntity.badRequest().body("i think manouba is better for you!!!");
            }
            if (diffInDays < 30) {
                return ResponseEntity.badRequest().body("our internship programs must be at least one month !!");
            }
            InternshipRequest internship = internshipRepo.findById(id).orElse(null);
            if (internship == null) {
                return ResponseEntity.badRequest().body("internship request does not exist junk!!!");
            }
            if (internship.getStatus() == Status.REFUSE) {
                return ResponseEntity.badRequest().body("internship request resfused no need to modify it!!!");
            }
            internship.setCreatedat(datt);
            internship.setRtesume(saved.getRtesume());
            internship.setDepartement(saved.getDepartement());
            internship.setRtesume(saved.getRtesume());
            internship.setIntern(saved.getIntern());
            internship.setLettreMotivation(saved.getLettreMotivation());
            internship.setDesiredEndDate(saved.getDesiredEndDate());
            internship.setDesiredStartDate(saved.getDesiredStartDate());
            internship.setStatus(Status.ENATTENTE);
            return  ResponseEntity.ok(internship);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating internship");
        }
    }


    @Override
    public ResponseEntity<?> findById(Long id) {
        try{
        InternshipRequest inter = internshipRepo.findById(id).orElse(null);
        if (inter == null) {
            return ResponseEntity.badRequest().body("internship request doesn't exist!!!");
        }
        return  ResponseEntity.ok(inter);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating internship");
    }
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<InternshipRequest> intsh= internshipRepo.findAll();
        try{

            if(intsh.isEmpty()){
                return ResponseEntity.badRequest().body("mafama chay");
            }
            return  ResponseEntity.ok(intsh);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retreving Internships");
        }
    }

    @Override
    public List<InternshipRequest> deleteById(Long Id) {
         internshipRepo.deleteById(Id);
        return internshipRepo.findAll();
    }

    @Override
    public ResponseEntity<?> affecterInternOnternship(Long IdInternship, Long IdIntern) {
        InternshipRequest in = internshipRepo.findById(IdInternship).orElse(null);
        Intern i=internrepo.findById(IdIntern).orElse(null);
        try{

            if(in == null){
                return ResponseEntity.badRequest().body("internship request does not exist");
            }
            if(i == null){
                return ResponseEntity.badRequest().body("intern does not exist");
            }
            Intern intern= internrepo.findById(IdIntern).orElse(null);
            InternshipRequest internship=internshipRepo.findById(IdInternship).orElse(null);
            internship.setIntern(intern);
            internshipRepo.save(internship);
            return  ResponseEntity.ok(in);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error affectation");
        }

    }

    @Override
    public ResponseEntity<?> accecptInternship(Long idi) {
        try{
            InternshipRequest internship = internshipRepo.findById(idi).orElse(null);

            if(internship==null){
                return ResponseEntity.badRequest().body("mahouch mawjoud");
            }
            if(internship.getStatus() == Status.ACCEPTE){
                return ResponseEntity.badRequest().body("Already accepted ");
            }
            internship.setStatus(Status.ACCEPTE);
             internshipRepo.save(internship);
            return  ResponseEntity.ok(internship);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting Internship");
        }

    }

    @Override
    public ResponseEntity<?> refuseInternship(Long idi) {
        try{
            InternshipRequest internship = internshipRepo.findById(idi).orElse(null);

            if(internship==null){
                return ResponseEntity.badRequest().body("mahouch mawjoud");
            }
            if(internship.getStatus() == Status.ACCEPTE){
                return ResponseEntity.badRequest().body("Already accepted ");
            }
            internship.setStatus(Status.REFUSE);
            internshipRepo.save(internship);
            return  ResponseEntity.ok(internship);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting Internship");
        }

    }
    @Override
    public Map<Status, Long> countByStatus() {
        return internshipRepo.countByStatusMap();
    }

    @Override
    public List<Object[]> countbyDepartement() {
        return internshipRepo.countByDepartmentAndStatus(Status.ENATTENTE);
    }


}
