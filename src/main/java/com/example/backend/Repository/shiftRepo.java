package com.example.backend.Repository;

import com.example.backend.Entity.Shift;
import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface shiftRepo  extends JpaRepository<Shift,Long> {
    List<Shift> findByStartTime(Date date);

}
