package com.example.backend.Repository;

import com.example.backend.Entity.LeaveAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface leaveRepo extends JpaRepository<LeaveAuth,Long> {
}
