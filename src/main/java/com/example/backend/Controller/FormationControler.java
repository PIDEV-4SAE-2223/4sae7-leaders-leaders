package com.example.backend.Controller;
import com.example.backend.Entity.Formation;
import com.example.backend.Services.InFormationService;
import com.example.backend.dto.ResponseFormation;
import com.example.backend.generic.GenericController;
 import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")

@RestController
@AllArgsConstructor
@RequestMapping("/api/formation")
public class FormationControler extends GenericController<Formation, Long> {

    private final InFormationService iFormationService;


    @PostMapping("/addFormation")
    public Formation add(@RequestBody Formation  f) {
        return iFormationService.add(f);
    }

    @PostMapping(value="/uploadAndAssignImageToFormation/{idFormation}" ,consumes =  "multipart/form-data" )

    public ResponseEntity<?> uploadAndAssignImageToFormation(@RequestParam("image") MultipartFile file,@PathVariable Long idFormation) throws IOException {

        return iFormationService.uploadAndAssignImageToFormation(file,idFormation);
    }


    @PostMapping(value="/AssignImageToFormation/{idFormation}" )
    public ResponseEntity<Object> AssignImageToFormation(@PathVariable Long idImage , @PathVariable Long idFormation ) {
        return iFormationService.AssignImageToFormation(idImage,idFormation);
    }


    @PostMapping(value="/AssignCertificatToFormation/{idFormation}" )
    public ResponseEntity<Object> AssignCertificatToFormation(@PathVariable Long idCertificat , @PathVariable Long idFormation ) {
        return iFormationService.AssignCertificatToFormation(idCertificat,idFormation);
    }



    @PostMapping("/{idFormation}/participant/{idParticipant}")
    public ResponseEntity<Object> affectFormationToParticipant(@PathVariable Long idFormation, @PathVariable Long idParticipant) {
        return iFormationService.affectFormationToParticipant(idFormation, idParticipant);

    }

    @PostMapping("/{idFormation}/former/{idFormer}")
    public ResponseEntity<Object> affectFormationToFormer(@PathVariable Long idFormation, @PathVariable Long idFormer) {
        return iFormationService.affectFormationToFormer(idFormation, idFormer);
    }

    @GetMapping("/adequate/{idprofil}")
    @ResponseBody
    public ResponseFormation getAdequateFormations(@PathVariable Long idprofil) {

        return iFormationService.getAdequateFormations(idprofil);
    }


    @GetMapping("/advancedsearch/period/{period}")
    @ResponseBody
    public ResponseFormation searchFormationsByPeriod(@PathVariable int period) {
        return iFormationService.searchFormationsByPeriod(period);
    }

    @GetMapping("/advancedsearch/former/{formerId}")
    @ResponseBody
    public ResponseEntity<Set<Formation>> searchFormationsByFormer(@PathVariable Long formerId) {
        return iFormationService.searchFormationsByFormer(formerId);
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<Formation> getAll() {
        return (List<Formation>) iFormationService.getall();
    }

















}