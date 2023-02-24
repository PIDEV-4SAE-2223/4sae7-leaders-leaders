package com.example.backend.Services;


import com.example.backend.Entity.Role;
import com.example.backend.Entity.RoleEnum;
import com.example.backend.Entity.User;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService extends IGenericServiceImp<User,Long> implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;



    public User createNewUser(User user,Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
         Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRoles(userRoles);
          return userRepository.save(user);
    }

    @Override
    public void DeleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
