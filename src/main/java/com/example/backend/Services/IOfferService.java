package com.example.backend.Services;

import com.example.backend.Entity.Offer;
import com.example.backend.Entity.User;
import com.example.backend.generic.IGenericService;

import java.util.List;

public interface IOfferService extends IGenericService<Offer,Long> {

    Offer archiverOffre(Long id);
    List<Offer> listOffreArchive();
    List<Offer> listnonArchive();
}
