package com.example.backend.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
public class Formation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date start_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date end_date;

    private int period ;

    private float cost;

    @OneToOne
    Image img;

    @ManyToMany(mappedBy = "formations_particip", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<User> participants = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    User former;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Quizz> quizzes = new HashSet<>();
    @OneToOne
    private Certificat certificat;


}