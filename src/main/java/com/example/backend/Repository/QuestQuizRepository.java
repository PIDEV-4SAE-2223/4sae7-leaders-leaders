package com.example.backend.Repository;

import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Entity.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestQuizRepository extends JpaRepository<QuestQuiz, Long> {
    @Query("select questQuiz from QuestQuiz questQuiz where questQuiz.id = :idQuestQuizz")
    QuestQuiz findByIdQuestQuizz(@Param("idQuestQuizz") Long idQuestQuizz);

    @Query("select questQuiz from QuestQuiz questQuiz where questQuiz.id = :idQuestQuizz")
    List<QuestQuiz> findAllByIdQuestQuizz(@Param("idQuestQuizz") Long idQuestQuizz);
}