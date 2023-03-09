package com.example.backend.Services;

import com.example.backend.Entity.Equiipment;
import com.example.backend.Entity.Offfer;
import com.example.backend.Repository.EquipmentRepository;
import com.example.backend.Repository.OffferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class OffferServiceImpl implements OffferService {
    private OffferRepository offferRepository;
    private EquipmentRepository equipmentRepository;
    @Override
    public Offfer saveAndaddOfferToEquipment(Offfer offfer, Long idEquipment) {

        Equiipment equipment = equipmentRepository.findById(idEquipment).get();
        offfer.setDateCreation(new Date(System.currentTimeMillis()));
        offfer.setArchive(offfer.isArchive());
        offfer.setEquipment((equipment));
        return offferRepository.save(offfer);
    }

}