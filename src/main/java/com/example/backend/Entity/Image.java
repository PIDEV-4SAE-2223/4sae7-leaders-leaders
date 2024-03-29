package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;
    private String type;
    @Lob
    private byte[] imageData;
    private String url;

//    @Lob
//    //used to indicate that the annotated attribute should be persisted in a "large object" field in DB
//    private byte[] imageData;

    @OneToOne(mappedBy = "img")
    @JsonIgnore
    Formation f ;

    @OneToOne(mappedBy = "img")
    @JsonIgnore
    Certificat c ;

}


