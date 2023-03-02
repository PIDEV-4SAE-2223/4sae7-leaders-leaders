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
public class MedicalReport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicalReport;

    private String reportBlob;

    @ManyToOne
    @JoinColumn(name = "appointment_id_appointement")
    private Appointment appointment;

}

