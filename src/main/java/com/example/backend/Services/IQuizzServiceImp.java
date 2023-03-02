package com.example.backend.Services;

import com.example.backend.Entity.Quizz;
import com.example.backend.Repository.QuizzRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class IQuizzServiceImp extends IGenericServiceImp<Quizz,Long> implements IQuizzService{
    private final QuizzRepository quizzRepository;

}