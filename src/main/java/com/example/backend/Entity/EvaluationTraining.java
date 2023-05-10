package com.example.backend.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.example.backend.Entity.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class EvaluationTraining implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore

    private Long id;
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String question5;
    private String question6;
    private String question7;
    private boolean question8c1;
    private boolean question8c2;
    private boolean question8c3;
    private String question9;
    private String comment;

    @JsonIgnore
    private int score;
    @OneToOne
    @JoinColumn(name = "trainer_id")
    @JsonIgnore
    private User trainer;

    @OneToOne()
    @JsonIgnore
    @JoinColumn(name = "learner_id")
    private User learner;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
}
