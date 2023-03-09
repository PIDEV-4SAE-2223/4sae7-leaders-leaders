package com.example.backend.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offfer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long idOffer;

    private Date dateCreation;
    private String descriptionOffer;
    private boolean archive;
    @OneToOne(mappedBy = "offer")
    Equiipment equipment;

}
