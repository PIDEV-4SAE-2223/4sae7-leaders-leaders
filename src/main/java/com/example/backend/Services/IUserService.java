package com.example.backend.Services;

import com.example.backend.Entity.User;
import com.example.backend.generic.IGenericService;

public interface IUserService extends IGenericService<User,Long>
{

    public User createNewUser(User user,Integer id);
    public void DeleteUser(Long id);
}
