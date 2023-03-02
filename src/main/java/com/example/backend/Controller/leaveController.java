package com.example.backend.Controller;

import com.example.backend.Entity.LeaveAuth;
import com.example.backend.Entity.User;
import com.example.backend.Services.IService;
import com.example.backend.Services.IUserService;
import com.example.backend.Services.IleaveService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping("/leave")
public class leaveController {

    private final IleaveService serviceleave;
    private final IUserService userserv;

    @PostMapping("/addLeave")
    public ResponseEntity<?> addLeave(@RequestBody LeaveAuth i)
    {
        try{
        LocalDate now =  LocalDate.now();//localdate Type
        Date datt= Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing to date
        long diff = i.getStartDate().getTime() - datt.getTime();//difference between two dates
        long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//convert result in days
            if(i.getStartDate().before(i.getEndDate())){
                return ResponseEntity.badRequest().body("Start Date could not be befor End Date!!!!");
            }
            if(diffInDays > 2){
                return ResponseEntity.badRequest().body("You Can't apply with in 2 days!!!!");
            }
         serviceleave.create(i);
            return  ResponseEntity.ok(i);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving Leave");
        }
    }

    @PostMapping("/affectLeaveUser/{idl}/{idu}")
    public ResponseEntity<?> affectLeaveUser(@PathVariable Long idl,@PathVariable Long idu)
    {
        try{
        LeaveAuth l = serviceleave.findById(idl);
        User u = userserv.retrieveById(idu);
            if(l == null){
                return ResponseEntity.badRequest().body("leave does not exist !!!!");
            }
            if(u == null){
                return ResponseEntity.badRequest().body("user does not exist!!!!");
            }
        serviceleave.affecterLeaveUser(idl,idu);
            return  ResponseEntity.ok(l);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error affecting Leave to user");
        }
    }
    @PutMapping("/updateLeave/{id}")//we have to change the selct arg by cin passed in the request
    public ResponseEntity<?> updateLeave(@RequestBody LeaveAuth leave,@PathVariable Long id)
    {
        try{
            LocalDate now =  LocalDate.now();//localdate Type
            Date datt= Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing to date
            long diff = leave.getStartDate().getTime() - datt.getTime();//difference between two dates
            long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//convert result in days
            if(leave.getStartDate().before(leave.getEndDate())){
                return ResponseEntity.badRequest().body("Start Date could not be befor End Date!!!!");
            }
            if(diffInDays > 2){
                return ResponseEntity.badRequest().body("You Can't apply with in 2 days!!!!");
            }
            LeaveAuth l=serviceleave.findById(id);
            l.setReason(leave.getReason());
            l.setStartDate(leave.getStartDate());
            l.setEndDate(leave.getEndDate());
            l.setVerification(l.getVerification());
            serviceleave.create(l);
            return  ResponseEntity.ok(leave);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Leave");
        }
    }

    @GetMapping("/getLeave")
    public List<LeaveAuth> getAllLeaves()
    {
        return serviceleave.findAll();
    }


    @GetMapping("/getLeave/{Id}")
    public LeaveAuth getLeave(@PathVariable Long Id)
    {
        return serviceleave.findById(Id);
    }

    @DeleteMapping("/delLeave/{Id}")
    public List<LeaveAuth> deleteLeave(@PathVariable Long Id)
    {
        return serviceleave.deleteById(Id);
    }
}
