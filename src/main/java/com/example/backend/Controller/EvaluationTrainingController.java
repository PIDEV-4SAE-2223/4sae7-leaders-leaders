package com.example.backend.Controller;


import com.example.backend.Services.IEvaluationTrainingService;
import com.example.backend.dto.ResponseEvaluationsTrainer;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.Entity.EvaluationTraining;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/feedback")
@AllArgsConstructor
public class EvaluationTrainingController extends GenericController<EvaluationTraining,Long> {


    private IEvaluationTrainingService evaluationTrainingServices;

    @PostMapping(value= "/save/{idTrainer}/{idLearner}" , consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> saveFeedback(@PathVariable Long idTrainer, @PathVariable Long idLearner, @RequestBody EvaluationTraining evaluationTraining) {
        return  evaluationTrainingServices.saveAndAssignFeedback(idTrainer,idLearner,evaluationTraining);
    }

    @GetMapping("/get/training/{idtrainer}")
    @ResponseBody
    public ResponseEvaluationsTrainer getFeedbackByTrainer(@PathVariable Long idtrainer){
        return evaluationTrainingServices.getFeedbackByTrainer(idtrainer);
    }

    @GetMapping("/get/learner/{idtrainer}")
    @ResponseBody
    public ResponseEvaluationsTrainer getFeedbackByLearner(@PathVariable Long idLearner){
        return evaluationTrainingServices.getFeedbackByLearner(idLearner);
    }
}
