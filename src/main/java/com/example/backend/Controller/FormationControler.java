package com.example.backend.Controller;

import com.example.backend.Entity.Formation;
import com.example.backend.Services.IFormationService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/formation")
public class FormationControler extends GenericController<Formation,Long> {

    private final IFormationService iService;


    @PostMapping("/{idF}/{nomP}/{prenomP}")
    public Formation affectFormationToParticipant (@PathVariable Long idF, @PathVariable String nomP, @PathVariable String prenomP){
        return iService.affectFormationToParticipant(idF,nomP,prenomP);
    }

    @PostMapping("/{idF}/{nomF}/{prenomF}")
    public Formation affectFormationToFormer (@PathVariable Long idF, @PathVariable String nomF, @PathVariable String prenomF){
        return iService.affectFormationToFormer(idF,nomF,prenomF);
    }
}


