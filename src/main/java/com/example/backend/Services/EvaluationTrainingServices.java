package com.example.backend.Services;

import com.example.backend.Entity.Certificat;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.shiftRepo;
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
    private final com.example.backend.Repository.shiftRepo shiftRepo;


    @Override
    public boolean isEvaluationExist( Long idTrainer,Long idLearner) {
        List<EvaluationTraining> evoluation = evaluationTrainingRepository.findByTrainerIdAndLearnerId(idTrainer, idLearner);
        return !evoluation.isEmpty();
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
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0, q5 = 0, q6 = 0, q7 = 0, q8c1 = 0,  q8c2 = 0, q8c3 = 0, q9 = 0, score;

        boolean isQuestion8c1Selected = evaluationTraining.isQuestion8c1();
        boolean isQuestion8c2Selected = evaluationTraining.isQuestion8c2();
        boolean isQuestion8c3Selected = evaluationTraining.isQuestion8c3();

        if (isQuestion8c1Selected) {
            q8c1 = 5;
        }
        if (isQuestion8c2Selected) {
            q8c2 = 5;
        }
        if (isQuestion8c3Selected) {
            q8c3 = 5;
        }



        if (!evaluationTraining.getQuestion1().isEmpty()) {
            q1 = Integer.parseInt(evaluationTraining.getQuestion1());
        }
        if (!evaluationTraining.getQuestion2().isEmpty()) {
            q2 = Integer.parseInt(evaluationTraining.getQuestion2());
        }
        if (!evaluationTraining.getQuestion3().isEmpty()) {
            q3 = Integer.parseInt(evaluationTraining.getQuestion3());
        }
        if (!evaluationTraining.getQuestion4().isEmpty()) {
            q4 = Integer.parseInt(evaluationTraining.getQuestion4());
        }
        if (!evaluationTraining.getQuestion5().isEmpty()) {
            q5 = Integer.parseInt(evaluationTraining.getQuestion5());
        }
        if (!evaluationTraining.getQuestion6().isEmpty()) {
            q6 = Integer.parseInt(evaluationTraining.getQuestion6());
        }
        if (!evaluationTraining.getQuestion7().isEmpty()) {
            q7 = Integer.parseInt(evaluationTraining.getQuestion7());
        }

        if (!evaluationTraining.getQuestion9().isEmpty()) {
            q9 = Integer.parseInt(evaluationTraining.getQuestion9());
        }
        score = q1 + q2 + q3 + q4 + q5 + q6 + q7 - q8c1 -q8c2  -q8c3 + q9;
        if (score<0)
           return 0;
        return score;
    }

    @Override
    public EvaluationTraining saveAndAssignFeedback(@PathVariable Long idTrainer, @PathVariable Long idLearner, @RequestBody EvaluationTraining evaluationTraining) {
        User userTrainer = userRepository.findById(idTrainer).orElseThrow(() -> new NotFoundException("Id trainer not found"));
        User userLearner = userRepository.findById(idLearner).orElseThrow(() -> new NotFoundException("Id learner not found"));
        String checkPathVariable = this.checkUserByPathVariable(userTrainer, userLearner);
       if (!checkPathVariable.equals("valid")) {
           return null;
        }
        boolean isEvaluationExist = this.isEvaluationExist(idTrainer, idLearner);
       System.out.println((isEvaluationExist));
        if (isEvaluationExist==true) {
            return null;
        }


        evaluationTraining.setCreatedAt(new Date());
        evaluationTraining.setScore(this.calculScore(evaluationTraining));

        evaluationTraining.setTrainer(userTrainer);
        evaluationTraining.setLearner(userLearner);

        EvaluationTraining saveFeedback = evaluationTrainingRepository.save(evaluationTraining);

        return saveFeedback;

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