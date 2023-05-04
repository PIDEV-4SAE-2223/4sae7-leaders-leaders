package com.example.backend.dto;


import com.example.backend.Entity.Formation;
import java.io.Serializable;
import java.util.List;
public class ResponseFormation implements Serializable {
    private List<Formation> formations;
    public ResponseFormation(List<Formation> formations) {
        this.formations = formations;
    }
    public List<Formation> getFormations() {
        return formations;
    }
    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }
}
