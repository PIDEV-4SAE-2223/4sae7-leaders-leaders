package com.example.backend.Services;

import com.example.backend.Entity.Formation;
import com.example.backend.Entity.RoleEnum;
import com.example.backend.Entity.User;
import com.example.backend.Repository.FormationRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IFormationServiceImp extends IGenericServiceImp< Formation ,Long> implements IFormationService{

    private final FormationRepository formationRepository;
    private final UserRepository userRepository;


       @Override
       public Formation affectFormationToParticipant(Long idF, String firstName, String lastName) {

            Formation f = this.retrieveById(idF);
            User p = userRepository.findByFirstnameAndLastnameAndRolesContains(firstName,lastName, RoleEnum.ROLE_USER);
            if(f != null && p!= null){

                f.getParticipants().add(p);


            }

            return formationRepository.save(f);
        }



    @Override
    public Formation affectFormationToFormer(Long idF, String nom, String prenom) {

        Formation f = this.retrieveById(idF);
        User p = userRepository.findByFirstnameAndLastnameAndRolesContains(nom,prenom, RoleEnum.ROLE_FORMATEUR);
        if(f != null && p!= null){

            f.getFormers().add(p);


        }

        return formationRepository.save(f);
    }


}
