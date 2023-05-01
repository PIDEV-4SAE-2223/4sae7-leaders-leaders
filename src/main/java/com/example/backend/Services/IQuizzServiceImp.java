package com.example.backend.Services;

import com.example.backend.Entity.Formation;
import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Entity.Quizz;
import com.example.backend.Repository.QuestQuizRepository;
import com.example.backend.Repository.QuizzRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class IQuizzServiceImp extends IGenericServiceImp<Quizz,Long> implements IQuizzService{
    private final QuizzRepository quizzRepository;
    private final InFormationService iserviceFormation;
    private final QuestQuizRepository questQuizRepository;

    @Override
    public ResponseEntity<Object> addQuizz(@PathVariable Long idFormation, @RequestBody Quizz quizz) {
        Formation formation = iserviceFormation.retrieveById(idFormation);
        if (formation == null  ) {
            return ResponseEntity.ok("Id formation is required!");
        }
        if (formation.getQuizzes().size()<2) {
            if (quizz.getTitle() == null) {
                return ResponseEntity.ok("Title is required!");
            }
            try {
                quizz.setFormation(formation);
                Quizz addQuizz = quizzRepository.save(quizz);
                return ResponseEntity.ok(addQuizz);
            } catch (Exception ex) {
                return ResponseEntity.ok(ex.getMessage());
            }
        }
        return ResponseEntity.ok("You already added 2 Quizzs for this Training");
    }

    @Override
    public ResponseEntity<Object> addAndAssignQuestionQuizz(@PathVariable Long idQuizz, @RequestBody QuestQuiz questionQuizz) {
        Quizz quizz = quizzRepository.findByIdQuizz(idQuizz);
        if (quizz == null) {
            return ResponseEntity.ok("Id quizz is required!");
        }
        try {
            questionQuizz.setQuizz(quizz);
            QuestQuiz question = questQuizRepository.save(questionQuizz);
            return ResponseEntity.ok(question);
        }catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }


    @Override
    public ResponseEntity<Object> addAndAssignManyQuestionsQuizz(@PathVariable Long idQuizz, @RequestBody List<QuestQuiz> manyquestionsQuizz)  {
        Quizz quizz = quizzRepository.findByIdQuizz(idQuizz);
        if (quizz == null) {
            return ResponseEntity.ok("Id quizz is required!");
        }
        try {
            int i=1;
            for(QuestQuiz question : manyquestionsQuizz) {
                question.setQuizz(quizz);
                questQuizRepository.save(question);
                i++;
                if (i > 10) {
                    break;
                }
            }
            return ResponseEntity.ok(manyquestionsQuizz);
        }catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }


    @Override
    public ResponseEntity<Object> updateQuestionsQuizz(@PathVariable Long idQuestQuizz, @RequestBody QuestQuiz questionQuizz)  {
        Optional<QuestQuiz> questQuizz = questQuizRepository.findById(idQuestQuizz);
        if (!questQuizz.isPresent()) {
            return ResponseEntity.ok("Id questQuizz is required!");
        }
        try {

            questionQuizz.setId(idQuestQuizz);
            questionQuizz.setQuestion(questionQuizz.getQuestion());
            questionQuizz.setQuizz(questQuizz.get().getQuizz());
            QuestQuiz questionsQuizz =  questQuizRepository.save(questionQuizz);
            return ResponseEntity.ok(questionsQuizz);
        }catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }


    @Override
    public ResponseEntity<Quizz> getQuizz(@PathVariable Long idQuizz){
        Quizz quizz = quizzRepository.findByIdQuizz(idQuizz);
        if (quizz == null) {
            ResponseEntity.ok("Quizz not found !");
        }
        return ResponseEntity.ok(quizz);
    }



}