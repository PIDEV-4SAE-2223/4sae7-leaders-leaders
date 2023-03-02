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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shift implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    @Temporal(TemporalType.DATE)
    Date startTime;
    @Temporal(TemporalType.DATE)
    Date endTime;
    @Temporal(TemporalType.DATE)
    Date breakTime;
    int BreakDuration;

    //@JsonIgnore
    @ManyToMany
    Set<User> users = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL,mappedBy="shift")
    Set<Intern> interns=new HashSet<>();

}
