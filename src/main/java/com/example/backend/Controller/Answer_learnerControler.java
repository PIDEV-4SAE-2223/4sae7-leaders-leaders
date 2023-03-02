package com.example.backend.Controller;

import com.example.backend.Entity.Answer_learner;
import com.example.backend.Services.IAnswer_learnerService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/response_learner")
public class Answer_learnerControler extends GenericController<Answer_learner,Long> {

    private final IAnswer_learnerService iService;


}
