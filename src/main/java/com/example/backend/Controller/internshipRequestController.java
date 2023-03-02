package com.example.backend.Controller;

import com.example.backend.Entity.Equipment;
import com.example.backend.Entity.Intern;
import com.example.backend.Entity.InternshipRequest;
import com.example.backend.Services.IService;
import com.example.backend.Services.InternService;
import com.example.backend.Services.InternshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/internship")
public class internshipRequestController {

    private final InternshipService internshipservice;
    private final InternService internService;


    @PostMapping("/addInternship")
    public ResponseEntity<?> addInternship(@RequestBody InternshipRequest i)
    {
        try{
            LocalDate now =  LocalDate.now();//now Type localdate
            Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
            if(i.getDesiredStartDate().before(i.getDesiredEndDate())){
                return ResponseEntity.badRequest().body("Start Date could not be befor End Date!!!!");
            }
            if (i.getDesiredStartDate().after(datt)) {
                return ResponseEntity.badRequest().body("check today's date junk!!!");
            }
             internshipservice.create(i);
            return  ResponseEntity.ok(i);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving internship");
        }
    }

    @PostMapping("/affectInbterInternshiip/{idinter}/{idinternship}")
    public ResponseEntity<?> affectInternInternship(@PathVariable Long idinter, @PathVariable Long idinternship)
    {
        InternshipRequest in = internshipservice.findById(idinternship);
        Intern i =internService.findById(idinter);
        try{

            if(in == null){
                return ResponseEntity.badRequest().body("internship request does not exist");
            }
            if(i == null){
                return ResponseEntity.badRequest().body("intern does not exist");
            }
            internshipservice.affecterInternOnternship(idinternship,idinter);
            return  ResponseEntity.ok(in);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error affectation");
        }
    }

    @GetMapping("/getInternships")
    public ResponseEntity<?> getAllInternships()
    {
        List<InternshipRequest> intsh= internshipservice.findAll();
        try{

            if(intsh.isEmpty()){
                return ResponseEntity.badRequest().body("mafama chay");
            }
            return  ResponseEntity.ok(intsh);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retreving Internships");
        }
    }


    @GetMapping("/getInternship/{Id}")
    public ResponseEntity<?> getInternship(@PathVariable Long Id)
    {
        InternshipRequest in=  internshipservice.findById(Id);
        try{

            if(in==null){
                return ResponseEntity.badRequest().body("mahouch mawjoud");
            }
            return  ResponseEntity.ok(in);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retreving Internship");
        }
    }

    @DeleteMapping("/delInternship/{Id}")
    public List<InternshipRequest> deleteInternship(@PathVariable Long Id)
    {
        return internshipservice.deleteById(Id);
    }

    @PutMapping("/acceptappointment/{Id}")
    public ResponseEntity<?> acceptAppointement(@PathVariable Long Id)
    {
        try{
            InternshipRequest internship =internshipservice.findById(Id);

            if(internship==null){
                return ResponseEntity.badRequest().body("mahouch mawjoud");
            }
            if(internship.isStatus()){
                return ResponseEntity.badRequest().body("Already accepted ");
            }
            internshipservice.accecptInternship(internship);
            return  ResponseEntity.ok(internship);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting Internship");
        }
    }

}

