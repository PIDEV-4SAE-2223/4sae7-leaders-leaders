package com.example.backend.Controller;

import com.example.backend.Entity.Offer;
import com.example.backend.Services.IOfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/offer")
public class offerController {

    private IOfferService Ioffer;

    @PostMapping("/addOffer")
    public ResponseEntity<?> addOffer(@RequestBody Offer o)
    {
        try{
            if(o.getQuantity() < 1){
                return ResponseEntity.badRequest().body("you have to set up the quantity you need");
            }
        LocalDate now =  LocalDate.now();//now Type localdate
        Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
        o.setDateCreation(datt);
        Offer offer = Ioffer.add(o);
            return  ResponseEntity.ok(offer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving offer");
        }
    }

    @PutMapping("/updateOffer/{id}")//we have to change the selct arg by cin passed in the request
    public ResponseEntity<?> updateOffer(@RequestBody Offer o,@PathVariable Long id)
    {
        try{
            if(o.getQuantity() <1){
                return ResponseEntity.badRequest().body("you have to set up the quantity you need");
            }
        LocalDate now =  LocalDate.now();//now Type localdate
        Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
        Offer off=Ioffer.retrieveById(id);
        off.setEquipment(o.getEquipment());
        off.setDescriptionOffer(o.getDescriptionOffer());
        off.setQuantity(o.getQuantity());
        o.setDateCreation(datt);
        Offer offer= Ioffer.add(off);
            return  ResponseEntity.ok(offer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving equipment");
        }
    }
    @GetMapping("/getOffers")
    public List<Offer> getAllOffers()
    {
        return Ioffer.retrieveAll();
    }

    @GetMapping("/getOffer/{Id}")
    public Offer getOffer(@PathVariable Long Id)
    {
        return Ioffer.retrieveById(Id);
    }

    @DeleteMapping("/deleOffer/{Id}")
    public  void deleteOffer(@PathVariable Long Id)
    {
        Ioffer.delete(Id);
    }
    @PutMapping("/archiveroffre/{id}")
    public Offer archiverOffre(@PathVariable Long id)
    {
        return Ioffer.archiverOffre(id);
    }
    @GetMapping("/listArchive")
    public List<Offer> getArchives()
    {
        return Ioffer.listOffreArchive();
    }
    @GetMapping("/listNoArchive")
    public List<Offer> getNonArchives()
    {
        return Ioffer.listnonArchive();
    }
}
