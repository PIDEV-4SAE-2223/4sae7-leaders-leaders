package com.example.backend.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class AnalysisCenter implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnalysisCenter;

    private String title;

    private String address;

     private Integer phoneNumber;

     private String webSite;



}

