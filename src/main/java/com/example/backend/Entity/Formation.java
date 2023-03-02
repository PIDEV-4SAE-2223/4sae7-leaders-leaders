package com.example.backend.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Formation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    String name;


    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date start_date;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date end_date;
    @JsonIgnore
    int period=0;
    public void setPeriod(int period) {
        int p;
        p = ((int) ((start_date.getTime() - end_date.getTime()) / (24 * 60 * 60 * 1000)));
        this.period = Math.abs(p);
    }

    float cost;

    @ManyToMany( mappedBy = "formations_particip", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<User> participants=new HashSet<>();

    @ManyToMany( mappedBy = "formations_former", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<User> formers=new HashSet<>();



    @OneToMany( mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Quizz> quizzes=new HashSet<>();

    @ManyToOne
    Certificat  certificat;
}



