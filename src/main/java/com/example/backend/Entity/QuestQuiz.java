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
public class QuestQuiz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String question;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quizz quizz;


    @OneToMany( mappedBy = "quest_quiz", cascade = CascadeType.ALL)
    Set<Proposition> propositions=new HashSet<>();

    public void setId(Long idQuestQuizz) {
        this.id = idQuestQuizz;
    }



}
