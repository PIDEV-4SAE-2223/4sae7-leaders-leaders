package com.example.backend.dto;


import com.example.backend.Entity.Formation;
import org.springframework.core.io.Resource;

import java.io.Serializable;

public class FormationDTO implements Serializable {
    private Formation formation;
    private Resource imageFile;

    public void setFormation(Formation formation){
        this.formation=formation;
    }
    public void setImageFile(Resource imageFile)
    {
        this.imageFile=imageFile;
    }
}