package com.example.backend.Services;

import com.example.backend.Entity.Equiipment;
import com.example.backend.Entity.Offfer;

import java.util.List;

public interface OffferService {
    public Offfer saveAndaddOfferToEquipment(Offfer offfer ,Long idEquipment);
    public Offfer findById(Long id);
    public List<Offfer> findAll();


    public void deleteById(Long id);
    public Offfer updateOfffer(Long id,Offfer offfer);

}
