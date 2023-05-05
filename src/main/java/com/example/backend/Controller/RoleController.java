package com.example.backend.Controller;


import com.example.backend.Entity.Role;
import com.example.backend.Services.IRoleService;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController extends GenericController<Role, Integer> {

    private IRoleService roleService;


}
