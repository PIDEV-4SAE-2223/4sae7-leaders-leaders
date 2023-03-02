package com.example.backend.Services;

import com.example.backend.Entity.Answer_learner;
import com.example.backend.Repository.Answer_learnerRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IAnswer_learnerServiceImp extends IGenericServiceImp<Answer_learner,Long> implements IAnswer_learnerService {
    private final Answer_learnerRepository answer_learnerRepository;



}