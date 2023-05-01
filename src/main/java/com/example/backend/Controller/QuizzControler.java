package com.example.backend.Controller;

import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Entity.Quizz;
import com.example.backend.Services.IQuizzService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizz")
public class QuizzControler extends GenericController<Quizz,Long> {

    private final IQuizzService iQuizzService;

    @PostMapping("/add/{idFormation}")
    @ResponseBody
    public ResponseEntity<Object> addQuizz(@PathVariable Long idFormation, @RequestBody Quizz quizz) {
        return iQuizzService.addQuizz(idFormation,quizz)   ;
    }

    @PostMapping("/add/question/{idQuizz}")
    @ResponseBody
    public ResponseEntity<Object> addQuestionQuizz(@PathVariable Long idQuizz, @RequestBody QuestQuiz questionQuizz) {
        return iQuizzService.addAndAssignQuestionQuizz(idQuizz,questionQuizz);
    }

    @PostMapping("/add/questions/{idQuizz}")
    @ResponseBody
    public ResponseEntity<Object> addQuestionsQuizz(@PathVariable Long idQuizz, @RequestBody List<QuestQuiz> manyquestionQuizz)   {
        try {
            return iQuizzService.addAndAssignManyQuestionsQuizz(idQuizz,manyquestionQuizz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update/question/{idQuestQuizz}")
    @ResponseBody
    public ResponseEntity<Object> updateQuestionsQuizz(@PathVariable Long idQuestQuizz, @RequestBody QuestQuiz questionQuizz)  {
        try {
            return iQuizzService.updateQuestionsQuizz(idQuestQuizz,questionQuizz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/id/{idQuizz}")
    @ResponseBody
    public ResponseEntity<Quizz> getQuizz(@PathVariable Long idQuizz) {

        return iQuizzService.getQuizz(idQuizz);
    }
}
