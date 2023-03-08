package com.example.backend.Repository;

import com.example.backend.Entity.LeaveAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface leaveRepo extends JpaRepository<LeaveAuth,Long> {


    //nombre total de congés pris par un employé
    @Query("SELECT COUNT(l) FROM LeaveAuth l WHERE l.userr.id = :userId")
    Long getTotalLeavesByUserId(@Param("userId") Long userId);

    // nombre total de congés par raison
    @Query("SELECT l.reason, COUNT(l) FROM LeaveAuth l GROUP BY l.reason")
    List<Object[]> getLeavesByReason();

    //nombre total de congés approuvés
    @Query("SELECT COUNT(l) FROM LeaveAuth l WHERE l.status = 'ACCEPTE'")
    Long getTotalApprovedLeaves();

    List<LeaveAuth> findByUserrId(Long userId);
   // List<LeaveAuth> findByUserrIdAnd(Long userId, Date startTime, Date endTime);
}
