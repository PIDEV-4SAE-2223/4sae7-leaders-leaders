package com.example.backend.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Intern implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
     Long cin;
    String firstName;
    String lastName;
    String email;

    @OneToMany(mappedBy = "intern")//many to many could be better
    Set<InternshipRequest> internshipRequests = new HashSet<>();
    @JsonIgnore
    @ManyToOne
    Shift shift;

}
