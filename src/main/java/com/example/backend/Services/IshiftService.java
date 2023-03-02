package com.example.backend.Services;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;

import java.util.Date;
import java.util.List;

public interface IshiftService extends IService<Shift>{
    void affecterShioftUser(Long ids, Long idu);
    List<Shift> getShiftsByStartTime(Date datt);
    List<Intern> getInterns(Date datt);
    Shift affectInternToShfit(Long ids,Long idi);
    List<User> getIUsers(Date datt);
}
