package com.example.backend.Services;

import com.example.backend.Entity.Formation;
import com.example.backend.Entity.User;
import com.example.backend.generic.IGenericService;

import java.util.List;
import java.util.Set;

public interface IFormationService extends IGenericService<Formation, Long> {
    User affectFormationToParticipant(Formation formation, User participant);

    User affectFormationToFormer(Formation formation, User former);

    List<Formation> searchFormations(String keyword);

    List<Formation> searchFormationsByPeriod(int period);
}

