package com.example.backend.Services;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.shiftRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class serviceShift implements IshiftService{

    private final shiftRepo shiftrepo;
    private final UserRepository userrepo;
    private final internRepo interpo;
    @Override
    public Shift create(Shift saved) {
        //startTIme must be before EndTime && breakTime must be between start time and end time
        //if(saved.getStartTime().before(saved.getEndTime()) && saved.getBreakTime().after(saved.getStartTime())&& saved.getBreakTime().before(saved.getEndTime()))
        return shiftrepo.save(saved);

    }

    @Override
    public Shift findById(Long id) {
        return shiftrepo.findById(id).orElse(null);
    }

    @Override
    public List<Shift> findAll() {
        return shiftrepo.findAll();
    }

    @Override
    public List<Shift> deleteById(Long Id) {
         shiftrepo.deleteById(Id);
         return shiftrepo.findAll();
    }

    @Override
    public void affecterShioftUser(Long ids, Long idu) {
        User u=userrepo.findById(idu).orElse(null);
        Shift s=shiftrepo.findById(ids).orElse(null);
        s.getUsers().add(u);
        shiftrepo.save(s);
    }

    @Override
    public List<Shift> getShiftsByStartTime(Date datt) {
        return shiftrepo.findByStartTime(datt);
    }

    @Override
    public List<Intern> getInterns(Date datt) {
        return interpo.findByShift_StartTime(datt);
    }
    @Override
    public List<User> getIUsers(Date datt) {
        return userrepo.findByShifts_StartTime(datt);
    }
    @Override
    public Shift affectInternToShfit(Long ids,Long idi)
    {
        Shift s=shiftrepo.findById(ids).orElse(null);
        Intern i=interpo.findById(idi).orElse(null);
        s.getInterns().add(i);
        i.setShift(s);
        interpo.save(i);
        return shiftrepo.save(s);
    }
}
