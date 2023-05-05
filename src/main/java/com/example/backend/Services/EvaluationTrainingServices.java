package com.example.backend.Services;

import com.example.backend.Entity.Certificat;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.dto.ResponseEvaluationsTrainer;
import com.example.backend.generic.IGenericServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.EvaluationTraining;
import com.example.backend.Repository.EvaluationTrainingRepository;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EvaluationTrainingServices extends IGenericServiceImp<EvaluationTraining,Long> implements IEvaluationTrainingService {

    private final EvaluationTrainingRepository evaluationTrainingRepository;
    private final UserRepository userRepository;



    @Override
    public boolean isEvaluationExist(Long idLearner, Long idTrainer) {
        List<EvaluationTraining> evolution = evaluationTrainingRepository.findByIdTrainerAndIdLearner(idTrainer, idLearner);
        return !evolution.isEmpty();
    }


    @Override
    public List<EvaluationTraining> findAllEvaluationRepository() {
        List<EvaluationTraining> evolutions = evaluationTrainingRepository.findAll();
        return evolutions;
    }


    @Override
    public List<EvaluationTraining> findEvaluationsByTrainer(Long idTrainer) {
        List<EvaluationTraining> evolutionsTrainer = evaluationTrainingRepository.findByIdTrainer(idTrainer);
        return evolutionsTrainer;
    }

    public List<EvaluationTraining> findEvaluationsByLearner(Long idLearner) {
        List<EvaluationTraining> evolutionsLearner = evaluationTrainingRepository.findByIdLearner(idLearner);
        return evolutionsLearner;
    }

    @Override
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

    @Override
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

    @Override
    public ResponseEntity<Object> saveAndAssignFeedback(@PathVariable Long idTrainer, @PathVariable Long idLearner, @RequestBody EvaluationTraining evaluationTraining) {
        User userTrainer = userRepository.findById(idTrainer).orElseThrow(() -> new NotFoundException("Id trainer not found"));
        User userLearner = userRepository.findById(idLearner).orElseThrow(() -> new NotFoundException("Id learner not found"));
        String checkPathVariable = this.checkUserByPathVariable(userTrainer, userLearner);
        if (!checkPathVariable.equals("valid")) {
            return ResponseEntity.ok(checkPathVariable);
        }
        boolean isEvaluationExist = this.isEvaluationExist(idTrainer, idLearner);
        if (isEvaluationExist) {
            return ResponseEntity.ok("Evaluation already exist!");
        }
        try {
            evaluationTraining.setTrainer(userTrainer);
            evaluationTraining.setLearner(userLearner);
            evaluationTraining.setCreated_at(new Date());
            evaluationTraining.setScore(this.calculScore(evaluationTraining));
            EvaluationTraining saveFeedback = evaluationTrainingRepository.save(evaluationTraining);
            return ResponseEntity.ok(saveFeedback);
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @Override
    public ResponseEvaluationsTrainer getFeedbackByTrainer(Long idtrainer){
        List<EvaluationTraining> evaluationsTrainer = this.findEvaluationsByTrainer(idtrainer);
        ResponseEvaluationsTrainer response = new ResponseEvaluationsTrainer(evaluationsTrainer);
        return response;
    }

    @Override
    public ResponseEvaluationsTrainer getFeedbackByLearner( Long idLearner){
        List<EvaluationTraining> evaluationsTrainer = this.findEvaluationsByLearner(idLearner);
        ResponseEvaluationsTrainer response = new ResponseEvaluationsTrainer(evaluationsTrainer);
        return response;
    }
}