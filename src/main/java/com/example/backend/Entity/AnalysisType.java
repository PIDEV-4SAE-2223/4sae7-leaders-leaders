package com.example.backend.Entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AnalysisType implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnalysis;

    private String title;

    private String description;

     private Date analysisDelay;

    @ToString.Exclude
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "restriction_idrestriction")
    private Restriction restriction;

}
