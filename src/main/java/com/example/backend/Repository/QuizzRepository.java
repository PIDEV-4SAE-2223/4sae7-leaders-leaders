package com.example.backend.Repository;

import com.example.backend.Entity.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizzRepository extends JpaRepository<Quizz, Long> {
    @Query("select quizz from Quizz quizz where quizz.id = :idQuizz")
    Quizz findByIdQuizz(@Param("idQuizz") Long idQuizz);
}