package com.example.backend.Services;

import com.example.backend.Entity.Formation;
import com.example.backend.Entity.User;
import com.example.backend.Repository.FormationRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class IFormationServiceImp extends IGenericServiceImp<Formation, Long> implements IFormationService {

    private final UserRepository userRepository;

    private final FormationRepository formationRepository;

    public User affectFormationToParticipant(Formation formation, User participant) {
        Set<Formation> formations = new HashSet<>();
        formations.add(formation);
        participant.setFormations_particip(formations);
        return userRepository.save(participant);
    }

    public User affectFormationToFormer(Formation formation, User Former) {
        Set<Formation> formations = new HashSet<>();
        formations.add(formation);
        Former.setFormations_former(formations);
        return userRepository.save(Former);
    }

    public List<Formation> searchFormations(String keyword) {
        List<Formation> formations = formationRepository.findFormationsBySkills(keyword);
        return formations;
    }

    public List<Formation> searchFormationsByPeriod(int period){
        List<Formation> formations = formationRepository.searchCritireaPeriod(period);
        return formations;
    }
}
