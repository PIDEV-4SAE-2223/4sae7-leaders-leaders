package com.example.backend.Controller;

import com.example.backend.Entity.Formation;
import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Entity.Quizz;
import com.example.backend.Repository.FormationRepository;
import com.example.backend.Repository.QuestQuizRepository;
import com.example.backend.Repository.QuizzRepository;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizz")
public class QuizzControler extends GenericController<Quizz,Long> {
    @Autowired
    private QuestQuizRepository questQuizRepository;

    @Autowired
    private QuizzRepository quizzRepository;

    @Autowired
    private FormationRepository formationRepository;

    @PostMapping("/add/{idFormation}")
    @ResponseBody
    public ResponseEntity<Object> addQuizz(@PathVariable Long idFormation, @RequestBody Quizz quizz) {
        Formation formation = formationRepository.findByIdFormation(idFormation);
        if (formation == null) {
            return ResponseEntity.ok("Id formation is required!");
        }
        if (quizz.getTitle() == null) {
            return ResponseEntity.ok("Title is required!");
        }
        try {
            quizz.setFormation(formation);
            Quizz addQuizz = quizzRepository.save(quizz);
            return ResponseEntity.ok(addQuizz);
        }catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PostMapping("/add/question/{idQuizz}")
    @ResponseBody
    public ResponseEntity<Object> addQuestionQuizz(@PathVariable Long idQuizz, @RequestBody QuestQuiz questionQuizz) {
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

    @PostMapping("/add/questions/{idQuizz}")
    @ResponseBody
    public ResponseEntity<Object> addQuestionsQuizz(@PathVariable Long idQuizz, @RequestBody List<QuestQuiz> questionsQuizz) throws Exception {
        Quizz quizz = quizzRepository.findByIdQuizz(idQuizz);
        if (quizz == null) {
            return ResponseEntity.ok("Id quizz is required!");
        }
        try {
            for(QuestQuiz question : questionsQuizz) {
                question.setQuizz(quizz);
                questQuizRepository.save(question);
            }
            return ResponseEntity.ok(questionsQuizz);
        }catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @PutMapping("/update/question/{idQuestQuizz}")
    @ResponseBody
    public ResponseEntity<Object> updateQuestionsQuizz(@PathVariable Long idQuestQuizz, @RequestBody QuestQuiz questionQuizz) throws Exception {
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

    @GetMapping("/id/{idQuizz}")
    @ResponseBody
    public ResponseEntity<Quizz> getQuizz(@PathVariable Long idQuizz){
        Quizz quizz = quizzRepository.findByIdQuizz(idQuizz);
        if (quizz == null) {
            ResponseEntity.ok("Quizz not found !");
        }
        return ResponseEntity.ok(quizz);
    }
}
