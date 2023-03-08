package com.example.backend.Services;

import com.example.backend.Entity.LeaveAuth;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IleaveService extends IService<LeaveAuth> {

     ResponseEntity<?> affecterLeaveUser(Long Idl, Long idu);

     Long calculateLeaveDuration(LeaveAuth leaveAuth);

     List<LeaveAuth> getAllLeavesByUserId(Long userId);
     Long alldaysLeaves(Long iD);
}