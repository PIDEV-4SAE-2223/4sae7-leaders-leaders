package com.example.backend.Services;

import com.example.backend.Entity.Equiipment;
import com.example.backend.Entity.Offfer;
import com.example.backend.Repository.EquipmentRepository;
import com.example.backend.Repository.OffferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public Offfer findById(Long id) {return offferRepository.findById(id).orElse(null);}

    @Override
    public List<Offfer> findAll() {return  offferRepository.findAll();}

    @Override
    public void deleteById(Long id) {offferRepository.deleteById(id);}

    @Override
    public Offfer updateOfffer(Long id, Offfer offfer) {
        Offfer of= offferRepository.findById(id).get();
        of.setArchive(offfer.isArchive());
        of.setDateCreation(new Date(System.currentTimeMillis()));
        of.setEquipment(offfer.getEquipment());
        of.setDescriptionOffer(offfer.getDescriptionOffer());
        return offferRepository.save(offfer);
    }

}