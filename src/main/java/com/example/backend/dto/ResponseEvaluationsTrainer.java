package com.example.backend.dto;

import com.example.backend.Entity.EvaluationTraining;

import java.io.Serializable;
import java.util.List;

public class ResponseEvaluationsTrainer implements Serializable {

    private List<EvaluationTraining> evaluationsTrainer;

    public ResponseEvaluationsTrainer(List<EvaluationTraining> evaluationsTrainer) {
        this.evaluationsTrainer = evaluationsTrainer;
    }

    public List<EvaluationTraining> getEvaluationsTrainer() {
        return evaluationsTrainer;
    }

    public void setEvaluationsTrainer(List<EvaluationTraining> evaluationsTrainer) {
        this.evaluationsTrainer = evaluationsTrainer;
    }
}
