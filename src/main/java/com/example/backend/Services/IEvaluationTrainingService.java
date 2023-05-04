package com.example.backend.Services;

import com.example.backend.Entity.EvaluationTraining;
import com.example.backend.Entity.User;
import com.example.backend.dto.ResponseEvaluationsTrainer;
import com.example.backend.generic.IGenericService;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IEvaluationTrainingService extends IGenericService<EvaluationTraining,Long> {
    boolean isEvaluationExist(Long idLearner, Long idTrainer);
    List<EvaluationTraining>  findAllEvaluationRepository();
    List<EvaluationTraining> findEvaluationsByTrainer(Long idTrainer) ;
    List<EvaluationTraining> findEvaluationsByLearner(Long idLearner);
    String checkUserByPathVariable(User userTrainer, User userLearner) ;
    int calculScore(EvaluationTraining evaluationTraining);
    ResponseEntity<Object> saveAndAssignFeedback(Long idTrainer, Long idLearner, EvaluationTraining evaluationTraining) ;
    ResponseEvaluationsTrainer getFeedbackByTrainer(Long idtrainer);
    ResponseEvaluationsTrainer getFeedbackByLearner( Long idLearner);

    }
