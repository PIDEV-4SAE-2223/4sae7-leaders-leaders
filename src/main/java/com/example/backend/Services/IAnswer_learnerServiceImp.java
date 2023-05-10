package com.example.backend.Services;

import com.example.backend.Entity.AnswerLearner;
import com.example.backend.Repository.Answer_learnerRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IAnswer_learnerServiceImp extends IGenericServiceImp<AnswerLearner,Long> implements IAnswer_learnerService {
    private final Answer_learnerRepository answer_learnerRepository;



}