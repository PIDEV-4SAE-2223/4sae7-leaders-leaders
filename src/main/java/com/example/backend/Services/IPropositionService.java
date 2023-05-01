package com.example.backend.Services;


import com.example.backend.Entity.Proposition;
import com.example.backend.dto.ResponseProposition;
import com.example.backend.generic.IGenericService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface   IPropositionService  extends IGenericService<Proposition,Long> {

        ResponseEntity<Object> addProposition(Long idQuestion,Proposition proposition) throws Exception;
        ResponseProposition<List<Object>> addMultiProposition(Long idQuestion, List<Proposition> propositions) throws Exception;

                }