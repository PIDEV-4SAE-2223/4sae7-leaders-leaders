package com.example.backend.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.example.backend.Entity.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class EvaluationTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question_1;
    private String question_2;
    private String question_3;
    private String question_4;
    private String question_5;
    private String question_6;
    private String question_7;
    private String question_8;
    private String question_9;
    private String comment;
    private int score;
    @OneToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;
    @OneToOne()
    @JoinColumn(name = "learner_id")
    private User learner;
    private Date created_at;
}
