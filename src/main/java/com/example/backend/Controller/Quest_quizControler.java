package com.example.backend.Controller;

import com.example.backend.Entity.Quest_quiz;
import com.example.backend.Services.IQuest_quizService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quest_quiz")
public class Quest_quizControler extends GenericController<Quest_quiz,Long> {

    private final IQuest_quizService iService;


}
