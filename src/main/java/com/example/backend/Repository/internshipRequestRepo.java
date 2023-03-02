package com.example.backend.Repository;

import com.example.backend.Entity.InternshipRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface internshipRequestRepo extends JpaRepository<InternshipRequest,Long> {
}
