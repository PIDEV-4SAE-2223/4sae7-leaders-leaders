package com.example.backend.Controller;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;
import com.example.backend.Services.IService;
import com.example.backend.Services.IUserService;
import com.example.backend.Services.InternService;
import com.example.backend.Services.IshiftService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/shift")
public class shiftController {
    private final IshiftService serviceshift;
    private final IUserService userserv;
    private final InternService internserv;

    @PostMapping("/addShift")
    public ResponseEntity<?> addShift(@RequestBody Shift p)
    {
        try{
            LocalDate now =  LocalDate.now();//now Type localdate
            Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
            if(p.getStartTime().before(datt)){
                return ResponseEntity.badRequest().body("Start Date could not be in the past!!!!");
            }
            if(p.getBreakTime().before(p.getStartTime())){
                return ResponseEntity.badRequest().body("Break could not be before the shift!!!!");
            }
            if(p.getBreakTime().after(p.getEndTime())){
                return ResponseEntity.badRequest().body("Break could not be after the shift!!!!");
            }
            if(p.getStartTime().after(p.getEndTime())){
                return ResponseEntity.badRequest().body("End Date could not be before Start Date!!!!");
            }
        p.setBreakDuration(2);
         serviceshift.create(p);
            return  ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving equipment");
        }
    }

    @PutMapping("/affectShiftUser/{idu}/{ids}")
    public ResponseEntity<?> affectShiftUser(@PathVariable Long idu,@PathVariable Long ids)
    {
        User u=userserv.retrieveById(idu);
        Shift s=serviceshift.findById(ids);
        try{

            if(u==null){
                return ResponseEntity.badRequest().body("404 User not found!!!!");
            }
            if(s==null){
                return ResponseEntity.badRequest().body("404 shift not found!!!!");
            }

        serviceshift.affecterShioftUser(ids,idu);
            return  ResponseEntity.ok(s);
        }
         catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error operation affecting ");
        }
    }
    @PutMapping("/affectShiftIntern/{ids}/{idi}")
    public ResponseEntity<?> affectShiftIntern(@PathVariable Long ids,@PathVariable Long idi)
    {
        Shift shift=  serviceshift.findById(ids);
        Intern intern = internserv.findById(idi);
        try{
            if(shift == null) {
                return ResponseEntity.badRequest().body("404 Shift not found!!!!");
            }
            if(intern == null) {
                return ResponseEntity.badRequest().body("404 Intern  not found!!!!");
            }
            serviceshift.affectInternToShfit(ids,idi);
            return  ResponseEntity.ok(shift);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error operation affecting ");
        }
    }
    @PutMapping("/updateShift/{id}")//we have to change the selct arg by cin passed in the request
    public ResponseEntity<?> updateShift(@RequestBody Shift p,@PathVariable Long id)
    {
        try{
            LocalDate now =  LocalDate.now();//now Type localdate
            Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
            if(p.getStartTime().before(datt)){
                return ResponseEntity.badRequest().body("Start Date could not be in the past!!!!");
            }
            if(p.getBreakTime().before(p.getStartTime())){
                return ResponseEntity.badRequest().body("Break could not be before the shift!!!!");
            }
            if(p.getBreakTime().after(p.getEndTime())){
                return ResponseEntity.badRequest().body("Break could not be after the shift!!!!");
            }
            if(p.getStartTime().after(p.getEndTime())){
                return ResponseEntity.badRequest().body("End Date could not be before Start Date!!!!");
            }
        Shift shift=serviceshift.findById(id);
        shift.setBreakDuration(2);
        shift.setBreakTime(p.getBreakTime());
        shift.setStartTime(p.getStartTime());
        shift.setEndTime(p.getEndTime());
        serviceshift.create(shift);
            return  ResponseEntity.ok(shift);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating equipment");
        }
    }

    @GetMapping("/getShift")
    public List<Shift> getAllShifts()
    {
        return serviceshift.findAll();
    }


    @GetMapping("/getShift/{Id}")
    public Shift getShift(@PathVariable Long Id)
    {
        return serviceshift.findById(Id);
    }

    @DeleteMapping("/delShift/{Id}")
    public List<Shift> deleteShift(@PathVariable Long Id)
    {
        serviceshift.deleteById(Id);
        return serviceshift.findAll();
    }
    @GetMapping("/getShiftByDate")
    public List<Shift> getShiftsByStartDate(@DateTimeFormat(pattern = "yyyy-M-dd")Date datt)
    {
        return serviceshift.getShiftsByStartTime(datt);
    }
    @GetMapping("/getInternsByDate")
    public List<Intern> getInternsByShift(@DateTimeFormat(pattern = "yyyy-M-dd")Date datt)
    {
        return serviceshift.getInterns(datt);
    }
    @GetMapping("/getUsersByDate")
    public List<User> getUserByShift(@DateTimeFormat(pattern = "yyyy-M-dd")Date datt)
    {
        return serviceshift.getIUsers(datt);
    }
}
