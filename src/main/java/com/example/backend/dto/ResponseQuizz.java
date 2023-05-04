package com.example.backend.dto;

import com.example.backend.Entity.Quizz;
import java.io.Serializable;
import java.util.List;

public class ResponseQuizz<O> implements Serializable {
    private List<Quizz> quizz;

    public ResponseQuizz(List<Quizz> quizz) {
        this.quizz = quizz;
    }

    public List<Quizz> getQuizz() {
        return quizz;
    }

    public void setQuizz(List<Quizz> quizz) {
        this.quizz = quizz;
    }
}
