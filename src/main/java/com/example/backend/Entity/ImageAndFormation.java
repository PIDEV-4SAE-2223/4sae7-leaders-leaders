package com.example.backend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageAndFormation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    public ImageAndFormation(byte[] image, Formation formation) {
        this.image = image;
        this.formation = formation;
    }

    public byte[] getImage() {
        return image;
    }

    public Formation getFormation() {
        return formation;
    }
}
