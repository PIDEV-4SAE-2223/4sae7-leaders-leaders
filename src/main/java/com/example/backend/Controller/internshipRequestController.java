package com.example.backend.Controller;

import com.example.backend.Entity.Equipment;
import com.example.backend.Entity.Intern;
import com.example.backend.Entity.InternshipRequest;
import com.example.backend.Entity.Status;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.internshipRequestRepo;
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

    @PostMapping("/addInternship")
    public ResponseEntity<?> addInternship(@RequestBody InternshipRequest i)
    {
            return internshipservice.create(i);
    }
    @PutMapping("/updateIntenrship/{Id}")
    public ResponseEntity<?> updateIntendhip(@RequestBody InternshipRequest i,@PathVariable Long Id)
    {
        return internshipservice.update(i,Id);
    }

    @PutMapping("/affectInbterInternshiip/{idinter}/{idinternship}")
    public ResponseEntity<?> affectInternInternship(@PathVariable Long idinter, @PathVariable Long idinternship)
    {
        return internshipservice.affecterInternOnternship(idinternship,idinter);
    }

    @GetMapping("/getInternships")
    public ResponseEntity<?> getAllInternships()
    {
        return internshipservice.findAll();
    }


    @GetMapping("/getInternship/{Id}")
    public ResponseEntity<?> getInternship(@PathVariable Long Id)
    {
        return internshipservice.findById(Id);
    }

    @DeleteMapping("/delInternship/{Id}")
    public List<InternshipRequest> deleteInternship(@PathVariable Long Id)
    {
        return internshipservice.deleteById(Id);
    }

    @PutMapping("/acceptInternship/{Id}")
    public ResponseEntity<?> acceptInternship(@PathVariable Long Id)
    {
       return internshipservice.accecptInternship(Id);
    }
    @PutMapping("/refuseInternship/{Id}")
    public ResponseEntity<?> refuseInternship(@PathVariable Long Id)
    {
        return internshipservice.refuseInternship(Id);
    }

}

