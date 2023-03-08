package com.example.backend.Repository;

import com.example.backend.Entity.RoleEnum;
import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

     @Query("select u from User u where u.email = ?1")
     Optional<User> findByEmail(String email);

      Boolean existsByEmail(String email);
     List<User> findByShifts_StartTime(Date datt);
     User findByFirstnameAndLastnameAndRolesContains(String firstName, String lastName, RoleEnum t);


//     @Query(value = "SELECT u FROM user u JOIN user_roles ur ON u.id_user = ur.user_id JOIN role r ON ur.roles_id = r.id_role WHERE r.role =?1",nativeQuery = true )
//       List<User> findAlluserByrole(RoleEnum roleEnum);

@Query("SELECT u FROM User u JOIN u.roles r WHERE r.role = ?1 ")
     List<User> findAllusersByrole(RoleEnum roleEnum);

 }
