package com.example.backend.Repository;

import com.example.backend.Entity.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
}