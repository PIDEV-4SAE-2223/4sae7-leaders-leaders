package com.example.backend.Services;


import com.example.backend.Entity.Role;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.generic.IGenericService;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
 public class RoleService extends IGenericServiceImp<Role,Integer> implements IRoleService {

      private RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void DeleteRole(Integer id) {
          roleRepository.deleteById(id);
    }
}
