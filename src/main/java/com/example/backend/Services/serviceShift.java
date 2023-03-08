package com.example.backend.Services;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.LeaveAuth;
import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.shiftRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class serviceShift implements IshiftService{

    private final shiftRepo shiftrepo;
    private final UserRepository userrepo;
    private final internRepo interpo;
    @Override
    public ResponseEntity<?> create(Shift saved) {
        try{
            LocalDate now =  LocalDate.now();//now Type localdate
            Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
            if(saved.getStartTime().before(datt)){
                return ResponseEntity.badRequest().body("Start Date could not be in the past!!!!");
            }
            if(saved.getBreakTime().before(saved.getStartTime())){
                return ResponseEntity.badRequest().body("Break could not be before the shift!!!!");
            }
            if(saved.getBreakTime().after(saved.getEndTime())){
                return ResponseEntity.badRequest().body("Break could not be after the shift!!!!");
            }
            if(saved.getStartTime().after(saved.getEndTime())){
                return ResponseEntity.badRequest().body("End Date could not be before Start Date!!!!");
            }
            saved.setBreakDuration(2);
            shiftrepo.save(saved);
            return  ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving equipment");
        }

    }

    @Override
    public ResponseEntity<?> update(Shift saved, Long id) {
        try{
            LocalDate now =  LocalDate.now();//now Type localdate
            Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
            if(saved.getStartTime().before(datt)){
                return ResponseEntity.badRequest().body("Start Date could not be in the past!!!!");
            }
            if(saved.getBreakTime().before(saved.getStartTime())){
                return ResponseEntity.badRequest().body("Break could not be before the shift!!!!");
            }
            if(saved.getBreakTime().after(saved.getEndTime())){
                return ResponseEntity.badRequest().body("Break could not be after the shift!!!!");
            }
            if(saved.getStartTime().after(saved.getEndTime())){
                return ResponseEntity.badRequest().body("End Date could not be before Start Date!!!!");
            }
            Shift shift=shiftrepo.findById(id).orElse(null);
            shift.setBreakDuration(2);
            shift.setBreakTime(saved.getBreakTime());
            shift.setStartTime(saved.getStartTime());
            shift.setEndTime(saved.getEndTime());
            shiftrepo.save(shift);
            return  ResponseEntity.ok(shift);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating equipment");
        }
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try{
            Shift inter = shiftrepo.findById(id).orElse(null);
            if (inter == null) {
                return ResponseEntity.badRequest().body("shift doesn't exist!!!");
            }
            return  ResponseEntity.ok(inter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retireving shift");
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Shift> intsh= shiftrepo.findAll();
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
    public List<Shift> deleteById(Long Id) {
         shiftrepo.deleteById(Id);
         return shiftrepo.findAll();
    }

    @Override
    public ResponseEntity<?> affecterShioftUser(Long ids, Long idu) {

        try{
            User u=userrepo.findById(idu).orElse(null);
            Shift s=shiftrepo.findById(ids).orElse(null);

            if(u==null){
                return ResponseEntity.badRequest().body("404 User not found!!!!");
            }
            if(s==null){
                return ResponseEntity.badRequest().body("404 shift not found!!!!");
            }
            s.getUsers().add(u);
            shiftrepo.save(s);
            return  ResponseEntity.ok(s);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error operation affecting ");
        }

    }

    @Override
    public List<Shift> getShiftsByStartTime(Date datt) {
        return shiftrepo.findByStartTime(datt);
    }

    @Override
    public List<Intern> getInterns(Date date) {
        LocalDateTime startOfDay = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        return shiftrepo.findAllInternsForDay(startOfDay, endOfDay);
    }
    @Override
    public List<User> getIUsers(Date date) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date startOfDay = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endOfDay = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                    .minusMillis(1));

            return shiftrepo.findAllUsersWithShiftsForDay(startOfDay, endOfDay);

    }
    @Override
    public ResponseEntity<?> affectInternToShfit(Long ids,Long idi)
    {
        Shift s=shiftrepo.findById(ids).orElse(null);
        Intern i=interpo.findById(idi).orElse(null);
        try{
            if(s == null) {
                return ResponseEntity.badRequest().body("404 Shift not found!!!!");
            }
            if(i == null) {
                return ResponseEntity.badRequest().body("404 Intern  not found!!!!");
            }
            s.getInterns().add(i);
            i.setShift(s);
            interpo.save(i);
             shiftrepo.save(s);
            return  ResponseEntity.ok(s);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error operation affecting ");
        }
    }


    @Override
    public List<Shift> getShiftsForDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        LocalDate startOfDay = date.atStartOfDay().toLocalDate();
        LocalDate endOfDay = startOfDay.plusDays(1);
        return shiftrepo.findAllByStartTimeBetween(startOfDay, endOfDay);
    }
    @Override
    public List<Shift> getShiftsForDay(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date startOfDay = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().minusMillis(1));


        return shiftrepo.findAllShiftsForDay(startOfDay, endOfDay);
    }

}
