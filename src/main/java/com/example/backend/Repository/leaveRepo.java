package com.example.backend.Repository;

import com.example.backend.Entity.LeaveAuth;
import com.example.backend.Entity.Status;
import com.example.backend.Entity.User;
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
    List<LeaveAuth> findByStatus(Status status);
    @Query("SELECT l FROM LeaveAuth l WHERE l.userr.id = :id AND l.status = :status")
    List<LeaveAuth> findAcceptedLeaveAuthsByUserId(@Param("id") Long id, @Param("status") Status status);

    @Query("SELECT l.userr.username, SUM(DATEDIFF(l.endDate, l.startDate)) "
            + "FROM LeaveAuth l "
            + "WHERE l.status = :status "
            + "GROUP BY l.userr.id")
    List<Object[]> getAcceptedLeaveDaysByUser(@Param("status") Status status);

    @Query("SELECT l.status, COUNT(l) FROM LeaveAuth l GROUP BY l.status")
    List<Object[]> getLeaveStatsByStatus();
    // List<LeaveAuth> findByUserrIdAnd(Long userId, Date startTime, Date endTime);

    @Query("SELECT l.userr FROM LeaveAuth l WHERE :currentDate BETWEEN l.startDate AND l.endDate AND l.status = :status")
    List<User> getUsersOnLeaveByStatus(@Param("currentDate") Date currentDate, @Param("status") Status status);

    @Query("SELECT CASE WHEN COUNT(la) > 0 THEN true ELSE false END FROM LeaveAuth la WHERE la.userr.id = :userId AND la.status = :status AND :currentDate BETWEEN la.startDate AND la.endDate")
    boolean isUserOnLeave(@Param("userId") Long userId, @Param("currentDate") Date currentDate, @Param("status") Status status);


    @Query("SELECT l.reason, COUNT(l.id) FROM LeaveAuth l GROUP BY l.reason")
    List<Object[]> getLeaveStatisticsByReason();
}
