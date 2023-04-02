package com.example.backend.Services;

import com.example.backend.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.EvaluationTraining;
import com.example.backend.Repository.EvaluationTrainingRepository;

import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationTrainingServices {
    @Autowired
    private final EvaluationTrainingRepository evaluationTrainingRepository;

    public void save(EvaluationTraining evaluationTraining) {
        evaluationTrainingRepository.save(evaluationTraining);
    }

    public boolean isEvaluationExist(Long idLearner, Long idTrainer) {
        List<EvaluationTraining> evolution = evaluationTrainingRepository.findByIdTrainerAndIdLearner(idTrainer, idLearner);
        return !evolution.isEmpty();
    }

    public List<EvaluationTraining> findAllEvaluationRepository() {
        List<EvaluationTraining> evolutions = evaluationTrainingRepository.findAll();
        return evolutions;
    }

    public List<EvaluationTraining> findEvaluationsByTrainer(Long idTrainer) {
        List<EvaluationTraining> evolutionsTrainer = evaluationTrainingRepository.findByIdTrainer(idTrainer);
        return evolutionsTrainer;
    }

    public List<EvaluationTraining> findEvaluationsByLearner(Long idLearner) {
        List<EvaluationTraining>  evolutionsLearner = evaluationTrainingRepository.findByIdLearner(idLearner);
        return evolutionsLearner;
    }

    public String checkUserByPathVariable(User userTrainer, User userLearner) {
        boolean isTrainer = userTrainer.getAuthorities().iterator().next().getAuthority().equals("ROLE_FORMATEUR");
        if (!isTrainer) {
            return "Error found: wrong id trainer";
        }
        boolean isLearner = userLearner.getAuthorities().iterator().next().getAuthority().equals("ROLE_USER");
        if (!isLearner) {
            return "Error found: wrong id learner";
        }
        return "valid";
    }

    public int calculScore(EvaluationTraining evaluationTraining) {
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0, q5 = 0, q6 = 0, q7 = 0, q8 = 0, q9 = 0, score;
        if (!evaluationTraining.getQuestion_1().isEmpty()) {
            q1 = Integer.parseInt(evaluationTraining.getQuestion_1());
        }
        if (!evaluationTraining.getQuestion_2().isEmpty()) {
            q2 = Integer.parseInt(evaluationTraining.getQuestion_2());
        }
        if (!evaluationTraining.getQuestion_3().isEmpty()) {
            q3 = Integer.parseInt(evaluationTraining.getQuestion_3());
        }
        if (!evaluationTraining.getQuestion_4().isEmpty()) {
            q4 = Integer.parseInt(evaluationTraining.getQuestion_4());
        }
        if (!evaluationTraining.getQuestion_5().isEmpty()) {
            q5 = Integer.parseInt(evaluationTraining.getQuestion_5());
        }
        if (!evaluationTraining.getQuestion_6().isEmpty()) {
            q6 = Integer.parseInt(evaluationTraining.getQuestion_6());
        }
        if (!evaluationTraining.getQuestion_7().isEmpty()) {
            q7 = Integer.parseInt(evaluationTraining.getQuestion_7());
        }
        if (!evaluationTraining.getQuestion_8().isEmpty()) {
            q8 = Integer.parseInt(evaluationTraining.getQuestion_8());
        }
        if (!evaluationTraining.getQuestion_9().isEmpty()) {
            q9 = Integer.parseInt(evaluationTraining.getQuestion_9());
        }
        score = q1 + q2 + q3 + q4 + q5 + q6 + q7 + q8 + q9;
        return score;
    }
}
