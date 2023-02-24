package com.example.backend.Services;

import com.example.backend.Entity.Role;
import com.example.backend.generic.IGenericService;

public interface IRoleService extends IGenericService<Role,Integer> {

    public Role createNewRole(Role role);
    public void DeleteRole(Integer id);

}
