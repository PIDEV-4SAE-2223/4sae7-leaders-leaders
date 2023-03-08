package com.example.backend.Entity;

import lombok.*;
import java.util.Date;
import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idNotif ;
    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equiipment equipment;
}
