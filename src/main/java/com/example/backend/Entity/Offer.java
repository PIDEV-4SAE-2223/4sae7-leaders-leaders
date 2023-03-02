package com.example.backend.Entity;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long idOffer;

    Date dateCreation;
    String descriptionOffer;
    int quantity;
    boolean archive;
    //Images


    @OneToOne
    Equipment equipment;

    @JsonIgnore
    @OneToMany(mappedBy = "offer")//many to many could be better
    Set<SupplierApplication> applications = new HashSet<>();

}
