package com.example.backend.Repository;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface shiftRepo  extends JpaRepository<Shift,Long> {
    List<Shift> findByStartTime(Date date);
    List<Shift> findAllByStartTimeBetween(LocalDate startOfDay, LocalDate endOfDay);
    @Query("SELECT s FROM Shift s WHERE s.startTime >= :startOfDay AND s.startTime < :endOfDay")
    List<Shift> findAllShiftsForDay(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);
    @Query("SELECT DISTINCT u FROM User u JOIN u.shifts s WHERE s.startTime BETWEEN :startOfDay AND :endOfDay")
    List<User> findAllUsersWithShiftsForDay(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);
    @Query("SELECT DISTINCT i FROM Intern i JOIN i.shift s WHERE s.startTime >= :startOfDay AND s.startTime < :endOfDay")
    List<Intern> findAllInternsForDay(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);




}
