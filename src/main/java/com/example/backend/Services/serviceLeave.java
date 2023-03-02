package com.example.backend.Services;


import com.example.backend.Entity.LeaveAuth;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.leaveRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class serviceLeave implements IleaveService{

    private final leaveRepo leaverepo;
    private final UserRepository userrepo;
    @Override
    public LeaveAuth create(LeaveAuth saved) {//leave must be 48h before requesting
       /* LocalDate now =  LocalDate.now();//localdate Type
        Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing to date
        long diff = saved.getStartDate().getTime() - datt.getTime();//difference between two dates
        long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//convert result in days
        if(saved.getStartDate().before(saved.getEndDate()) && diffInDays > 2 )*/
        return leaverepo.save(saved);

    }

    @Override
    public LeaveAuth findById(Long id) {
        return leaverepo.findById(id).orElse(null);
    }

    @Override
    public List<LeaveAuth> findAll() {
        return leaverepo.findAll();
    }

    @Override
    public List<LeaveAuth> deleteById(Long Id) {
        leaverepo.deleteById(Id);
        return leaverepo.findAll();
    }

    @Override
    public void affecterLeaveUser(Long Idl, Long idu) {
        User u=userrepo.findById(idu).orElse(null);
        LeaveAuth l=leaverepo.findById(Idl).orElse(null);
        l.setUserr(u);
        leaverepo.save(l);
    }
}
