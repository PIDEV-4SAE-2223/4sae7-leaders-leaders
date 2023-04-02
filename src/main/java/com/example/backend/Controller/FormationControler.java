package com.example.backend.Controller;

import com.example.backend.Entity.Formation;
import com.example.backend.Entity.User;
import com.example.backend.Repository.FormationRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.IFormationService;
import com.example.backend.dto.ResponseFormation;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/formation")
public class FormationControler extends GenericController<Formation, Long> {

    @Autowired
    private final FormationRepository formationRepository;

    @Autowired
    private IFormationService iService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{idFormation}/participant/{idParticipant}")
    public ResponseEntity<Object> affectFormationToParticipant(@PathVariable Long idFormation, @PathVariable Long idParticipant) {
        User participant = userRepository.findById(idParticipant).orElseThrow(() -> new NotFoundException("Id participant not found"));
        boolean isParticipant = participant.getAuthorities().iterator().next().getAuthority().equals("ROLE_USER");
        if (!isParticipant) {
            return ResponseEntity.ok("Error found: wrong id participant");
        }
        Formation formation = this.retrieveById(idFormation);
        if (formation == null) {
            return ResponseEntity.ok("Error found: formation not found");
        }
        try {
            User affectParticipant = iService.affectFormationToParticipant(formation, participant);
            return ResponseEntity.ok(affectParticipant.getFormations_particip());
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @PostMapping("/{idFormation}/former/{idFormer}")
    public ResponseEntity<Object> affectFormationToFormer(@PathVariable Long idFormation, @PathVariable Long idFormer) {
        User former = this.userRepository.findById(idFormer).orElseThrow(() -> new NotFoundException("Id former not found"));
        boolean isFormer = former.getAuthorities().iterator().next().getAuthority().equals("ROLE_FORMATEUR");
        if (!isFormer) {
            return ResponseEntity.ok("Error found: wrong id former");
        }
        Formation formation = this.retrieveById(idFormation);
        if (formation == null) {
            return ResponseEntity.ok("Error found: formation not found");
        }
        try {
            User affectFormer = this.iService.affectFormationToFormer(formation, former);
            return ResponseEntity.ok(affectFormer.getFormations_former());
        } catch (Exception ex) {
            return ResponseEntity.ok(ex.getMessage());
        }
    }

    @GetMapping("/adequate/{idprofil}")
    @ResponseBody
    public ResponseFormation getAdequateFormations(@PathVariable Long idprofil) {
        User account = this.userRepository.findById(idprofil).orElseThrow(() -> new NotFoundException("Id account not found"));
        String skills = account.getSkills();
        List<Formation> formations = this.iService.searchFormations(skills);
        ResponseFormation response = new ResponseFormation(formations);
        return response;
    }

    @GetMapping("/search/{keyword}")
    @ResponseBody
    public ResponseFormation searchFormations(@PathVariable String keyword) {
        List<Formation> formations = this.iService.searchFormations(keyword);
        ResponseFormation response = new ResponseFormation(formations);
        return response;
    }

    @GetMapping("/advancedsearch/period/{period}")
    @ResponseBody
    public ResponseFormation searchFormationsByPeriod(@PathVariable int period) {
        List<Formation> formations = this.iService.searchFormationsByPeriod(period);
        ResponseFormation response = new ResponseFormation(formations);
        return response;
    }

    @GetMapping("/advancedsearch/former/{formerId}")
    @ResponseBody
    public ResponseEntity<Set<Formation>> searchFormationsByFormer(@PathVariable Long formerId) {
        User account = this.userRepository.findById(formerId).orElseThrow(() -> new NotFoundException("Id account not found"));
        boolean isParticipant = account.getAuthorities().iterator().next().getAuthority().equals("ROLE_FORMATEUR");
        if (!isParticipant) {
            throw new NotFoundException("Error found: wrong role formater!");
        }
        Set<Formation> formations = account.getFormations_former();
        return ResponseEntity.ok(formations);
    }
}