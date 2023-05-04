package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certificat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    Long id;
    String name;
    String description;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    Date date;

    @JsonIgnore
    String pathQrcode;

    @OneToOne( mappedBy = "certificat"   /*, cascade = CascadeType.ALL*/)
    @JsonIgnore
    Formation formation;

    @OneToOne
    @JsonIgnore
    Image img ;


}
