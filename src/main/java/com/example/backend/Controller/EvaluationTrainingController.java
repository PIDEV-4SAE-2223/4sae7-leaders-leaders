package com.example.backend.Controller;

import com.example.backend.Entity.User;
import com.example.backend.Repository.EvaluationTrainingRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.dto.ResponseEvaluationsTrainer;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.Entity.EvaluationTraining;
import com.example.backend.Services.EvaluationTrainingServices;
import org.webjars.NotFoundException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class EvaluationTrainingController {
    @Autowired
    private EvaluationTrainingRepository evaluationTrainingRepository;
    @Autowired
    private UserRepository userRepository;

    private EvaluationTrainingServices evaluationTrainingServices;

    public EvaluationTrainingController(EvaluationTrainingServices trainerServices) {
        this.evaluationTrainingServices = trainerServices;
    }

    @PostMapping("/save/{idTrainer}/{idLearner}")
    @ResponseBody
    public ResponseEntity<Object> saveFeedback(@PathVariable Long idTrainer, @PathVariable Long idLearner, @RequestBody EvaluationTraining evaluationTraining) {
        User userTrainer = userRepository.findById(idTrainer).orElseThrow(() -> new NotFoundException("Id trainer not found"));
        User userLearner = userRepository.findById(idLearner).orElseThrow(() -> new NotFoundException("Id learner not found"));
        String checkPathVariable = this.evaluationTrainingServices.checkUserByPathVariable(userTrainer, userLearner);
        if (!checkPathVariable.equals("valid")) {
            return ResponseEntity.ok(checkPathVariable);
        }
        boolean isEvaluationExist = this.evaluationTrainingServices.isEvaluationExist(idTrainer, idLearner);
        if (isEvaluationExist) {
            return ResponseEntity.ok("Evaluation already exist!");
        }
        try {
            evaluationTraining.setTrainer(userTrainer);
            evaluationTraining.setLearner(userLearner);
            evaluationTraining.setCreated_at(new Date());
            evaluationTraining.setScore(this.evaluationTrainingServices.calculScore(evaluationTraining));
            EvaluationTraining saveFeedback = evaluationTrainingRepository.save(evaluationTraining);
            return ResponseEntity.ok(saveFeedback);
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @GetMapping("/get/training/{idtrainer}")
    @ResponseBody
    public ResponseEvaluationsTrainer getFeedbackByTrainer(@PathVariable Long idtrainer){
        List<EvaluationTraining> evaluationsTrainer = this.evaluationTrainingServices.findEvaluationsByTrainer(idtrainer);
        ResponseEvaluationsTrainer response = new ResponseEvaluationsTrainer(evaluationsTrainer);
        return response;
    }

    @GetMapping("/get/learner/{idtrainer}")
    @ResponseBody
    public ResponseEvaluationsTrainer getFeedbackByLearner(@PathVariable Long idLearner){
        List<EvaluationTraining> evaluationsTrainer = this.evaluationTrainingServices.findEvaluationsByLearner(idLearner);
        ResponseEvaluationsTrainer response = new ResponseEvaluationsTrainer(evaluationsTrainer);
        return response;
    }
}
