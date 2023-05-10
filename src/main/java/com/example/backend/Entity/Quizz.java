package com.example.backend.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quizz implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String title;
    private String description;


    private String training_name;

    @ManyToOne()
    @JoinColumn(name = "formation_id")
    @JsonIgnore
    private Formation formation;

    //unidirectionnel
    @ManyToMany()
    @JsonIgnore
    private Set<User> QuizzLearnes=new HashSet<>();

    @OneToMany( mappedBy = "quizz", cascade = CascadeType.ALL)

    Set<QuestQuiz> quest_quizs = new HashSet<>();


}
