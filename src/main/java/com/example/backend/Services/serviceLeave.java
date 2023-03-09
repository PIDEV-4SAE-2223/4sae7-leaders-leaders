package com.example.backend.Services;


import com.example.backend.Entity.*;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.leaveRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class serviceLeave implements IleaveService{

    private final leaveRepo leaverepo;
    private final UserRepository userrepo;
    @Override
    public ResponseEntity<?> create(LeaveAuth saved) {//leave must be 48h before requesting
        try{
            LocalDate now =  LocalDate.now();//localdate Type
            Date datt= Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing to date
            long diff = saved.getStartDate().getTime() - datt.getTime();//difference between two dates
            long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//convert result in days
            if(saved.getStartDate().after(saved.getEndDate())){
                return ResponseEntity.badRequest().body("Start Date could not be after End Date!!!!");
            }
           if(this.alldaysLeaves(saved.getUserr().getId()) > 30){
                return ResponseEntity.badRequest().body("you have reached your maximum days!!!!");
            }
            if(diffInDays < 2){
                return ResponseEntity.badRequest().body("You Can't apply with in 2 days!!!!");
            }
            saved.setStatus(Status.ENATTENTE);
         leaverepo.save(saved);
            return  ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving Leave");
        }

    }

    @Override
    public ResponseEntity<?> update(LeaveAuth saved, Long id) {
        try{
            LeaveAuth leave=leaverepo.findById(id).orElse(null);
            saved.setUserr(leave.getUserr());
            LocalDate now =  LocalDate.now();//localdate Type
            Date datt= Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing to date
            long diff = saved.getStartDate().getTime() - datt.getTime();//difference between two dates
            long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//convert result in days
            if(saved.getStartDate().after(saved.getEndDate())){
                return ResponseEntity.badRequest().body("Start Date could not be after End Date!!!!");
            }
            if(diffInDays < 2){
                return ResponseEntity.badRequest().body("You Can't apply with in 2 days!!!!");
            }
            if(this.alldaysLeaves(saved.getUserr().getId()) > 30){
                return ResponseEntity.badRequest().body("you have reached your maximum days!!!!");
            }
            leave.setStatus(Status.ENATTENTE);
            leave.setVerification(saved.getVerification());
            leave.setStartDate(saved.getStartDate());
            leave.setReason(saved.getReason());
            leave.setUserr(leave.getUserr());
            leave.setEndDate(saved.getEndDate());
            leaverepo.save(leave);
            return  ResponseEntity.ok(leave);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Leave");
        }
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try{
            LeaveAuth inter = leaverepo.findById(id).orElse(null);
            if (inter == null) {
                return ResponseEntity.badRequest().body("leave doesn't exist!!!");
            }
            return  ResponseEntity.ok(inter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retireving leave");
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<LeaveAuth> intsh= leaverepo.findAll();
        try{

            if(intsh.isEmpty()){
                return ResponseEntity.badRequest().body("mafama chay");
            }
            return  ResponseEntity.ok(intsh);
        } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retreving leaves");
        }
    }

    @Override
    public List<LeaveAuth> deleteById(Long Id) {
        leaverepo.deleteById(Id);
        return leaverepo.findAll();
    }

    @Override
    public ResponseEntity<?> affecterLeaveUser(Long Idl, Long idu) {
        try{
            LeaveAuth l = leaverepo.findById(Idl).orElse(null);
            User u = userrepo.findById(idu).orElse(null);
            if(l == null){
                return ResponseEntity.badRequest().body("leave does not exist !!!!");
            }
            if(u == null){
                return ResponseEntity.badRequest().body("user does not exist!!!!");
            }
            l.setUserr(u);
            leaverepo.save(l);
            return  ResponseEntity.ok(l);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error affecting Leave to user");
        }

    }

    @Override
    // Méthode pour calculer la durée d'un congé en jours
    public Long calculateLeaveDuration(LeaveAuth leaveAuth) {
        long diff = leaveAuth.getEndDate().getTime() - leaveAuth.getStartDate().getTime() ;//difference between two dates
        long kl = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return kl;
    }

    @Override
    public List<LeaveAuth> getAllLeavesByUserId(Long userId) {
        return leaverepo.findByUserrId(userId);
    }

    @Override
    public Long alldaysLeaves(Long id) {
        List<LeaveAuth> leaves = this.listesleavesAccepte(id);
        Long tot=0l;
        for(LeaveAuth leave:leaves ){
                tot += this.calculateLeaveDuration(leave);

        }
        return tot;
    }

    @Override
    public LeaveAuth acceptLeave(Long id)
    {
        LeaveAuth l =leaverepo.findById(id).orElse(null);
        l.setStatus(Status.ACCEPTE);
        return leaverepo.save(l);
    }

    @Override
    public LeaveAuth reffuseLeave(Long id) {
        LeaveAuth l =leaverepo.findById(id).orElse(null);
        l.setStatus(Status.REFUSE);
        return leaverepo.save(l);
    }

    @Override
    public List<LeaveAuth> listLeaves(Status stat) {
        return leaverepo.findByStatus(stat);
    }

    @Override
    public List<LeaveAuth> listesleavesAccepte(Long id) {
        return leaverepo.findAcceptedLeaveAuthsByUserId(id,Status.ACCEPTE);
    }
    @Override
    public List<Object[]> getAcceptedLeaveDaysByUser() {
        return leaverepo.getAcceptedLeaveDaysByUser(Status.ACCEPTE);
    }

    @Override
    public List<Object[]> getLeaveStatsByStatus() {
        return leaverepo.getLeaveStatsByStatus();
    }

    @Override
    public List<User> getUsersOnLeave(Date currentDate,Status stat) {
        return leaverepo.getUsersOnLeaveByStatus(currentDate,stat);
    }

    @Override
    public List<Object[]> getLeaveStatisticsByReason() {
        return leaverepo.getLeaveStatisticsByReason();
    }


}
