package com.example.backend.Repository;

import com.example.backend.Entity.RoleEnum;
import com.example.backend.Entity.Shift;
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
     List<User> findByShifts_StartTime(Date datt);
     User findByFirstnameAndLastnameAndRolesContains(String firstName, String lastName, RoleEnum t);

}    