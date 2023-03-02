package com.example.backend.Services;

import com.example.backend.Entity.LeaveAuth;

public interface IleaveService extends IService<LeaveAuth> {

    void affecterLeaveUser(Long Idl, Long idu);
}
