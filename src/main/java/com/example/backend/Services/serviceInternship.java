package com.example.backend.Services;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.InternshipRequest;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.internshipRequestRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class serviceInternship implements InternshipService {

    private final internshipRequestRepo internshipRepo;
    private final internRepo internrepo;
    @Override
    public InternshipRequest create(InternshipRequest saved) {//startdate must be before end date and after now
        /*LocalDate now =  LocalDate.now();//now Type localdate
        Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
        saved.setCreatedat(datt);
        if(saved.getDesiredStartDate().before(saved.getDesiredEndDate()) && saved.getDesiredStartDate().after(datt))*/
        return  internshipRepo.save(saved);
    }


    @Override
    public InternshipRequest findById(Long id) {
        return internshipRepo.findById(id).orElse(null);
    }

    @Override
    public List<InternshipRequest> findAll() {
        return internshipRepo.findAll();
    }

    @Override
    public List<InternshipRequest> deleteById(Long Id) {
         internshipRepo.deleteById(Id);
        return internshipRepo.findAll();
    }

    @Override
    public void affecterInternOnternship(Long IdInternship, Long IdIntern) {
        Intern intern= internrepo.findById(IdIntern).orElse(null);
        InternshipRequest internship=internshipRepo.findById(IdInternship).orElse(null);
        internship.setIntern(intern);
        internshipRepo.save(internship);
    }

    @Override
    public InternshipRequest accecptInternship(InternshipRequest internship) {
        internship.setStatus(true);
        return internshipRepo.save(internship);
    }

}
