package com.example.backend.Services;

import com.example.backend.Entity.Formation;
import com.example.backend.generic.IGenericService;



public interface IFormationService extends IGenericService<Formation,Long> {

    Formation affectFormationToParticipant (Long idF, String nom, String prenom);
    Formation affectFormationToFormer (Long idF, String nom, String prenom);


}

