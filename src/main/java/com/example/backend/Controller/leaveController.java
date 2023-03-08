package com.example.backend.Controller;

import com.example.backend.Entity.LeaveAuth;
import com.example.backend.Entity.User;
import com.example.backend.Repository.leaveRepo;
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
    @PostMapping("/addLeave/{idu}")
    public ResponseEntity<?> addLeave(@RequestBody LeaveAuth i,@PathVariable Long idu)
    {
        User u = userserv.retrieveById(idu);
        i.setUserr(u);
        return serviceleave.create(i);
    }

    @PutMapping("/affectLeaveUser/{idl}/{idu}")
    public ResponseEntity<?> affectLeaveUser(@PathVariable Long idl,@PathVariable Long idu)
    {

        return serviceleave.affecterLeaveUser(idl,idu);
    }
    @PutMapping("/updateLeave/{id}")//we have to change the selct arg by cin passed in the request
    public ResponseEntity<?> updateLeave(@RequestBody LeaveAuth leave,@PathVariable Long id)
    {
        return serviceleave.update(leave,id);
    }

    @GetMapping("/getLeaves")
    public ResponseEntity<?> getAllLeaves()
    {
        return serviceleave.findAll();
    }


    @GetMapping("/getLeave/{Id}")
    public ResponseEntity<?> getLeave(@PathVariable Long Id)
    {
        return serviceleave.findById(Id);
    }

    @DeleteMapping("/delLeave/{Id}")
    public List<LeaveAuth> deleteLeave(@PathVariable Long Id)
    {
        return serviceleave.deleteById(Id);
    }


    @GetMapping("/totaldays/{id}")
    public Long totaldays(@PathVariable Long id){
        return serviceleave.alldaysLeaves(id);
    }
}
