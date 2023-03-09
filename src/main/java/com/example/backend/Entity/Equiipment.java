package com.example.backend.Entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Equiipment implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Setter(AccessLevel.NONE)
        Long idEquipment;

        private String nameequipment;
        private String picture;
        private String description;

        private Date dateEndUsefullLife;

        @Enumerated(EnumType.STRING)
        private Status status;

        boolean favorite;

        @OneToOne()
        private Offfer offer;

        @OneToMany(mappedBy = "equipment",cascade = CascadeType.ALL)
        private List<Notification> notifications;

    }