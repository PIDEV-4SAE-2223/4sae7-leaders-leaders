package com.example.backend.Controller;


import com.example.backend.Services.IEvaluationTrainingService;
import com.example.backend.dto.ResponseEvaluationsTrainer;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.Entity.EvaluationTraining;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/api/feedback" ,produces = MediaType.APPLICATION_JSON_VALUE
)
@AllArgsConstructor
public class EvaluationTrainingController extends GenericController<EvaluationTraining,Long> {


    private IEvaluationTrainingService evaluationTrainingServices;

    @PostMapping(value= "/save/{idTrainer}/{idLearner}", consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<EvaluationTraining> saveFeedback(@PathVariable Long idTrainer, @PathVariable Long idLearner, @RequestBody EvaluationTraining evaluationTraining) {
        EvaluationTraining createdEvaluation=  evaluationTrainingServices.saveAndAssignFeedback(idTrainer,idLearner,evaluationTraining);
        return ResponseEntity.ok(createdEvaluation);
    }

    @GetMapping("/get/training/{idtrainer}")

    public ResponseEvaluationsTrainer getFeedbackByTrainer(@PathVariable Long idtrainer){
        return evaluationTrainingServices.getFeedbackByTrainer(idtrainer);
    }

    @GetMapping("/get/training/{idLearner}")
    public ResponseEvaluationsTrainer getFeedbackByLearner(@PathVariable Long idLearner){
        return evaluationTrainingServices.getFeedbackByLearner(idLearner);
    }
}
