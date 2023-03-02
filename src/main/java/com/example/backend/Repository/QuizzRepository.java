package com.example.backend.Repository;

import com.example.backend.Entity.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizzRepository extends JpaRepository<Quizz, Long> {
}