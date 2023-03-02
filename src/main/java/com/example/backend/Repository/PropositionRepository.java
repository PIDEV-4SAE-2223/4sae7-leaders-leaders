package com.example.backend.Repository;

import com.example.backend.Entity.Proposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropositionRepository extends JpaRepository<Proposition, Long> {
}