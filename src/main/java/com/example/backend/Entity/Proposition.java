package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Proposition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private int is_correct_answer;
    private String suggest;
    @ManyToOne
    @JoinColumn(name = "quest_quiz_id")
    @JsonIgnore
    private QuestQuiz quest_quiz;
    @ManyToMany( mappedBy = "answers", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Answer_learner> answer_learners = new HashSet<>();
}
