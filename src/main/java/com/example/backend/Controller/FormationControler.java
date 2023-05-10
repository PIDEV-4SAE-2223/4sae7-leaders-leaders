package com.example.backend.Controller;
import com.example.backend.Entity.Formation;
import com.example.backend.Entity.Image;
import com.example.backend.Entity.ImageAndFormation;
import com.example.backend.Entity.Quizz;
import com.example.backend.Repository.imageAndFormationRepository;
import com.example.backend.Services.IImageDbService;
import com.example.backend.Services.InFormationService;
import com.example.backend.dto.FormationDTO;
import com.example.backend.dto.ResponseFormation;
import com.example.backend.generic.GenericController;
 import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/formation" , produces = MediaType.APPLICATION_JSON_VALUE
)
public class FormationControler extends GenericController<Formation, Long> {

    private final InFormationService iFormationService;
    private final IImageDbService imageDataService;

    private final com.example.backend.Repository.imageAndFormationRepository imageAndFormationRepository;



    @GetMapping("/getQuizzs/{idF}")
    public Set<Quizz> getQuizzs(@PathVariable Long idF) {

        return iFormationService.getQuizzs(idF);
    }

    @PostMapping("/addFormation")
    public Formation add(@RequestBody Formation  f) {
        return iFormationService.add(f);
    }

    @PostMapping(value="/uploadAndAssignImageToFormation/{idFormation}" ,consumes =  "multipart/form-data" )

    public ResponseEntity<byte[]> uploadAndAssignImageToFormation(@RequestParam("image") MultipartFile file,@PathVariable Long idFormation) throws IOException {

         iFormationService.uploadAndAssignImageToFormation(file,idFormation);
        byte[] image = imageDataService.getById(((iFormationService.retrieveById(idFormation)).getImg()).getId());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }





    @PostMapping(value="/AssignImageToFormation/{idImage}/{idFormation}")
    public ResponseEntity<byte[]> AssignImageToFormation(@PathVariable Long idImage , @PathVariable Long idFormation ) {
         iFormationService.AssignImageToFormation(idImage,idFormation);
        byte[] image = imageDataService.getById(idImage);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
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



//    @GetMapping("/getFormationByid/{id}")
//    @ResponseBody
//    public Formation getAll(@PathVariable Long id) {
//        Formation f =iFormationService.retrieveById(id);
//        f.setImg(imageDataService.);
//    }


    @DeleteMapping("/deleteFormationAndAssociatedQuizzs/{id}")
    public Boolean deleteFormationAndAssociatedQuizzs(@PathVariable long id) {
        return	iFormationService.deleteFormationAndAssociatedQuizzs(id);
    }
    @GetMapping(value = "/getImageById/{idFormation}")
    public ResponseEntity<?> getImageById(@PathVariable Long idFormation) {
        Formation f=iFormationService.retrieveById(idFormation);

        if (f.getImg()!=null) {
            long id =f.getImg().getId();
            System.out.println(id);
            byte[] image = imageDataService.getById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(image);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found for formation ID: " + idFormation);
    }

    @GetMapping(value = "/getImageAndFormationById/{id}")
    public ResponseEntity<byte[]> getImageAndFormationById(@PathVariable Long id) {
        Optional<ImageAndFormation> imageAndFormationOptional = imageAndFormationRepository.findById(id);
        if (imageAndFormationOptional.isPresent()) {
            ImageAndFormation imageAndFormation = imageAndFormationOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<byte[]>(imageAndFormation.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




//    @GetMapping(value = "/getImageById/{idFormation}")
//    public ResponseEntity<?> getImageById(@PathVariable Long idFormation) {
//        Formation f = iFormationService.retrieveById(idFormation);
//        String imageUrl = f.getImg().getUrl();
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(imageUrl);
//    }
//





}