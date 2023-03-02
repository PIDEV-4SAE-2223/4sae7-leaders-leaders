package com.example.backend.Repository;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface internRepo extends JpaRepository<Intern,Long> {
    Intern findByEmail(String email);
    List<Intern> findByShift_StartTime(Date datt);
}
