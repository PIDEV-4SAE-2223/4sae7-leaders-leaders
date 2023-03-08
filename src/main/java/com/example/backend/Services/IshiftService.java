package com.example.backend.Services;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IshiftService extends IService<Shift>{
    ResponseEntity<?> affecterShioftUser(Long ids, Long idu);
    List<Shift> getShiftsByStartTime(Date datt);
    List<Intern> getInterns(Date datt);
    ResponseEntity<?> affectInternToShfit(Long ids,Long idi);
    List<User> getIUsers(Date datt);
    List<Shift> getShiftsForDate(String date);
    List<Shift> getShiftsForDay(Date date);
}
