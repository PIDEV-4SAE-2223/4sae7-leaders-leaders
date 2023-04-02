package com.example.backend.Controller;

import com.example.backend.Entity.Proposition;
import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Entity.Quizz;
import com.example.backend.Repository.PropositionRepository;
import com.example.backend.Repository.QuestQuizRepository;
import com.example.backend.Services.IPropositionService;
import com.example.backend.dto.ResponseProposition;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proposition")
public class PropositionControler extends GenericController<Proposition, Long> {

    @Autowired
    PropositionRepository propositionRepository;

    @Autowired
    QuestQuizRepository questQuizRepository;

    @PostMapping("/add/question/{idQuestion}")
    @ResponseBody
    public ResponseEntity<Object> addProposition(@PathVariable Long idQuestion, @RequestBody Proposition proposition) throws Exception {
        QuestQuiz questionsQuizz = questQuizRepository.findByIdQuestQuizz(idQuestion);
        if (questionsQuizz == null) {
            throw new Exception("Id quizz is required!");
        }
        try {
            proposition.setQuest_quiz(questionsQuizz);
            Proposition propositionRepo = propositionRepository.save(proposition);
            return ResponseEntity.ok(propositionRepo);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @PostMapping("/addmultiple/question/{idQuestion}")
    @ResponseBody
    public ResponseProposition<Proposition> addMultiProposition(@PathVariable Long idQuestion, @RequestBody List<Proposition> propositions) throws Exception {
        QuestQuiz questionsQuizz = questQuizRepository.findByIdQuestQuizz(idQuestion);
        if (questionsQuizz == null) {
            throw new Exception("Id quizz is required!");
        }
        try {
            List<Proposition> propositionsList = new ArrayList<>();
            for (Proposition proposition : propositions) {
                proposition.setQuest_quiz(questionsQuizz);
                Proposition propositionAdd = propositionRepository.save(proposition);
                propositionsList.add(propositionAdd);
            }
            ResponseProposition response = new ResponseProposition(propositionsList);
            return response;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
