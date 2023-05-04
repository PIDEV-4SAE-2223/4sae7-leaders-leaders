package com.example.backend.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Appointment implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAppointement;

    private LocalDateTime requestDate;

    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String analysisCenter;

    private boolean Is_Validated_By_Admin ;

    private String analysisType;

    private boolean isReportsReady;
    private String Email ;


    @ManyToOne
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "appointment", orphanRemoval = true)
    private Set<MedicalReport> medicalReports = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medecin_id_user")
    private User medecin;

    @ManyToOne
    AnalysisType type;
    @ManyToOne
    AnalysisCenter center;

}
