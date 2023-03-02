package com.example.backend.Services;

import com.example.backend.Entity.Quest_quiz;
import com.example.backend.Repository.Quest_quizRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class IQuest_quizServiceImp extends IGenericServiceImp<Quest_quiz,Long> implements IQuest_quizService{
    private final Quest_quizRepository quest_quizRepository;



}
