package com.example.backend.Repository;

import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.Entity.EvaluationTraining;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import java.util.List;

public interface EvaluationTrainingRepository extends JpaRepository<EvaluationTraining, Long> {
    @Query("select evaluationTraining from EvaluationTraining evaluationTraining where evaluationTraining.trainer.id = :idTrainer and evaluationTraining.learner.id = :idLearner")
    List<EvaluationTraining> findByIdTrainerAndIdLearner(@Param("idTrainer") Long idTrainer, @Param("idLearner") Long  idLearner);

    @Query("select evaluationTraining from EvaluationTraining evaluationTraining where evaluationTraining.trainer.id = :idTrainer")
    List<EvaluationTraining> findByIdTrainer(@Param("idTrainer") Long idTrainer);

    @Query("select evaluationTraining from EvaluationTraining evaluationTraining where evaluationTraining.trainer.id = :idLearner")
    List<EvaluationTraining> findByIdLearner(@Param("idLearner") Long  idLearner);
}
