package com.example.backend.Services;


import com.example.backend.Entity.Formation;
import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Entity.Quizz;
import com.example.backend.generic.IGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


public interface IQuizzService extends IGenericService<Quizz, Long> {

    ResponseEntity<Object> addQuizz( Long idFormation, Quizz quizz) ;
    ResponseEntity<Object> addAndAssignQuestionQuizz( Long idQuizz, QuestQuiz questionQuizz) ;

    ResponseEntity<Object> addAndAssignManyQuestionsQuizz( Long idQuizz,  List<QuestQuiz> manyquestionsQuizz);
    ResponseEntity<Object> updateQuestionsQuizz( Long idQuestQuizz,  QuestQuiz questionQuizz) ;
    ResponseEntity<Quizz> getQuizz( Long idQuizz);

    public Quizz assignQuizzToFormation(long idq, long idf);



}