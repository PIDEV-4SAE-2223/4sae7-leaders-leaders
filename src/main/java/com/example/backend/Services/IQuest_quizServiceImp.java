package com.example.backend.Services;

import com.example.backend.Entity.QuestQuiz;
import com.example.backend.Repository.QuestQuizRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class IQuest_quizServiceImp extends IGenericServiceImp<QuestQuiz,Long> implements IQuest_quizService{
    private final QuestQuizRepository quest_quizRepository;



}
