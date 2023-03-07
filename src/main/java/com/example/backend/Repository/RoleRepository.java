package com.example.backend.Repository;

import com.example.backend.Entity.Role;
import com.example.backend.Entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where r.role = ?1")
    Set<Role> findByRole(RoleEnum role);
 }