package com.example.backend.Services;

import com.example.backend.Entity.Equipment;
import com.example.backend.Entity.Offer;
import com.example.backend.Repository.offerRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OfferService extends IGenericServiceImp<Offer,Long> implements IOfferService{

    private final offerRepository offerrepo;

    @Override
    public Offer archiverOffre(Long id) {
        Offer off=offerrepo.findById(id).orElse(null);
        off.setArchive(true);
        return offerrepo.save(off);
    }

    @Override
    public List<Offer> listOffreArchive() {
        return offerrepo.findByArchiveIsTrue();
    }

    @Override
    public List<Offer> listnonArchive() {
        return offerrepo.findByArchiveIsFalse();
    }
}
