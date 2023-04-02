package com.example.backend.dto;

import com.example.backend.Entity.Proposition;
import java.io.Serializable;
import java.util.List;

public class ResponseProposition<O> implements Serializable {
    private List<Proposition> proposition;

    public ResponseProposition(List<Proposition> proposition) {
        this.proposition = proposition;
    }

    public List<Proposition> getProposition() {
        return proposition;
    }

    public void setProposition(List<Proposition> proposition) {
        this.proposition = proposition;
    }
}
