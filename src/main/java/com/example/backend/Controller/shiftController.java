package com.example.backend.Controller;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;
import com.example.backend.Repository.shiftRepo;
import com.example.backend.Services.IshiftService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/shift")
public class shiftController {
    private final IshiftService serviceshift;
    private final shiftRepo shiftrep;

    @PostMapping("/addShift")
    public ResponseEntity<?> addShift(@RequestBody Shift p) {
        return serviceshift.create(p);
    }

    @PutMapping("/affectShiftUser/{idu}/{ids}")
    public ResponseEntity<?> affectShiftUser(@PathVariable Long idu, @PathVariable Long ids) {
        return serviceshift.affecterShioftUser(idu, ids);
    }

    @PutMapping("/affectShiftIntern/{ids}/{idi}")
    public ResponseEntity<?> affectShiftIntern(@PathVariable Long ids, @PathVariable Long idi) {
        return serviceshift.affectInternToShfit(ids, idi);
    }

    @PutMapping("/updateShift/{id}")//we have to change the selct arg by cin passed in the request
    public ResponseEntity<?> updateShift(@RequestBody Shift p, @PathVariable Long id) {
        return serviceshift.update(p, id);
    }

    @GetMapping("/getShift")
    public ResponseEntity<?> getAllShifts() {
        return serviceshift.findAll();
    }


    @GetMapping("/getShift/{Id}")
    public ResponseEntity<?> getShift(@PathVariable Long Id) {
        return serviceshift.findById(Id);
    }

    @DeleteMapping("/delShift/{Id}")
    public ResponseEntity<?> deleteShift(@PathVariable Long Id) {
        serviceshift.deleteById(Id);
        return serviceshift.findAll();
    }

    @GetMapping("/getShiftByDate")
    public List<Shift> getShiftsByStartDate(@DateTimeFormat(pattern = "yyyy-M-dd") Date datt) {
        return serviceshift.getShiftsForDay(datt);
    }

    @GetMapping("/getInternsByDate")
    public List<Intern> getInternsByShift(@DateTimeFormat(pattern = "yyyy-M-dd") Date datt) {
        return serviceshift.getInterns(datt);
    }

    @GetMapping("/getUsersByDate")
    public List<User> getUserByShift(@DateTimeFormat(pattern = "yyyy-M-dd") Date datt) {
        return serviceshift.getIUsers(datt);
    }
}
