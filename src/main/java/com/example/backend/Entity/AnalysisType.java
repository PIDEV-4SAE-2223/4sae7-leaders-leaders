package com.example.backend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AnalysisType implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnalysis;
    private String title;
    private String description;
    private Date analysisDelay;
    private boolean isDangerous;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type",orphanRemoval = true)
    Set<Appointment> appointments=new HashSet<>();

}
