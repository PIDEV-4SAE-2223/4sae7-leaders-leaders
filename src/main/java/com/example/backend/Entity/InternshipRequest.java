package com.example.backend.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternshipRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    String lettreMotivation;
    @Enumerated(EnumType.STRING)
    Departement departement;
    String rtesume;// checkout type (could be file)
    @Temporal(TemporalType.DATE)
    Date createdat;
    @Temporal(TemporalType.DATE)
    Date DesiredStartDate;
    @Temporal(TemporalType.DATE)
    Date DesiredEndDate;
    @Enumerated(EnumType.STRING)
    Status status;


    @JsonIgnore
    @ManyToOne
    Intern intern;

}
