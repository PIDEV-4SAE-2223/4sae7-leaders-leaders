package com.example.backend.Services;

import com.example.backend.Entity.Equiipment;
import com.example.backend.Entity.Offfer;

import java.util.List;

public interface OffferService {
     Offfer saveAndaddOfferToEquipment(Offfer offfer ,Long idEquipment);
     Offfer findById(Long id);
     List<Offfer> findAll();
     void deleteById(Long id);
     Offfer updateOfffer(Long id,Offfer offfer);
    Offfer archiverOffre(Long id);
    List<Offfer> listOffreArchive();
    List<Offfer> listnonArchive();

}
