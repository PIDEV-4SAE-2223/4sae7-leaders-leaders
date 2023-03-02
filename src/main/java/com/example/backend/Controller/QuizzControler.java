package com.example.backend.Controller;

import com.example.backend.Entity.Quizz;
import com.example.backend.Services.IQuizzService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quizz")
public class QuizzControler extends GenericController<Quizz,Long> {

    private final IQuizzService iService;


}
