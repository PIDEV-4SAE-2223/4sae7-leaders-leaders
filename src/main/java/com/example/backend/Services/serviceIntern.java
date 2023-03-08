package com.example.backend.Services;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.InternshipRequest;
import com.example.backend.Entity.LeaveAuth;
import com.example.backend.Entity.Shift;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.shiftRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class serviceIntern implements InternService {

    private final internRepo internrepo;
    private final shiftRepo shiftrepo;


    @Override
    public ResponseEntity<?> create(Intern saved) {
        try{
            if(internrepo.findByEmail(saved.getEmail()) != null){
                return ResponseEntity.badRequest().body("email adress already existes");
            }
            internrepo.save(saved);
            return  ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving intern");
        }

    }

    @Override
    public ResponseEntity<?> update(Intern saved, Long id) {
        try{
            if(internrepo.findByEmail(saved.getEmail()) != null){
                return ResponseEntity.badRequest().body("email adress already existes");
            }
            Intern intern = internrepo.findById(id).orElse(null);
            intern.setShift(saved.getShift());
            intern.setEmail(saved.getEmail());
            intern.setFirstName(saved.getFirstName());
            intern.setLastName(saved.getLastName());
            intern.setInternshipRequests(saved.getInternshipRequests());
            internrepo.save(intern);
            return  ResponseEntity.ok(intern);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving intern");
        }
    }


    @Override
    public ResponseEntity<?> findById(Long id) {
        try{
            Intern inter = internrepo.findById(id).orElse(null);
            if (inter == null) {
                return ResponseEntity.badRequest().body("inter doesn't exist!!!");
            }
            return  ResponseEntity.ok(inter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retireving intern");
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Intern> intsh= internrepo.findAll();
        try{

            if(intsh.isEmpty()){
                return ResponseEntity.badRequest().body("mafama chay");
            }
            return  ResponseEntity.ok(intsh);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retreving interns");
        }
    }

    @Override
    public List<Intern> deleteById(Long Id) {
        internrepo.deleteById(Id);
        return internrepo.findAll();
    }

   /* @Override
    public void affecterShiftIntern(Long idShift,Long IdIntern) {
        Shift shiftIn=shiftrepo.findById(idShift).orElse(null);
        Intern internShift=internrepo.findById(IdIntern).orElse(null);
        internShift.setShift(shiftIn);
        internrepo.save(internShift);
    }*/
}
