package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Address is required")
    private String address;
    @NotNull(message = "Phone number is required")
    @Digits(integer = 8, fraction = 0, message = "Invalid phone number")
     private Integer phoneNumber;
     private String webSite;
     @JsonIgnore
     @OneToMany(cascade = CascadeType.ALL,mappedBy = "center",orphanRemoval = true)
    Set<Appointment> appointments=new HashSet<>();


}

