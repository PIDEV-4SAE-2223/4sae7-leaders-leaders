package com.example.backend.Controller;

import com.example.backend.Entity.AnswerLearner;
import com.example.backend.Services.IAnswer_learnerService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/response_learner", produces = MediaType.APPLICATION_JSON_VALUE)
public class Answer_learnerControler extends GenericController<AnswerLearner,Long> {

    private final IAnswer_learnerService iService;


}
