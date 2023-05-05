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
    @JsonIgnore
    private Long id;

    private int is_correct_answer;// 0 incorrect, 1 correct , une seule proposition correcte, le quizz est sur 10
    private String suggest;
    @ManyToOne
    @JoinColumn(name = "quest_quiz_id")
    @JsonIgnore
    private QuestQuiz quest_quiz;


    @ManyToMany(mappedBy = "propositions", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Answer_learner> answer_learners = new HashSet<>();

}
