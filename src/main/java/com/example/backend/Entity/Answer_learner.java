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
public class Answer_learner implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;

    //unidirectionnel
    @ManyToOne
    User user;


    @ManyToMany
    Set<Proposition> propositions = new HashSet<>();
}
//ylem les proposition men nafss l quest quizz