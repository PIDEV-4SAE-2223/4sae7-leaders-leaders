package com.example.backend.Controller;


import com.example.backend.Entity.Equipment;
import com.example.backend.Entity.Offer;
import com.example.backend.Entity.SupplierApplication;
import com.example.backend.Services.IApplicationService;
import com.example.backend.Services.IOfferService;
import com.example.backend.Services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/application")
public class ApplicationController {
     private final IApplicationService appserv;
     private final IOfferService offerserv;

     @PostMapping("/addApplication/{ido}")
    public ResponseEntity<?> addApplication(@RequestBody SupplierApplication supp, @PathVariable Long ido)
     {
       Offer off=offerserv.retrieveById(ido);
         try{
             if(off == null){
                 return ResponseEntity.badRequest().body("offer does not exist");
             }
             if(supp.getPriceGiven() == 0){
                 return ResponseEntity.badRequest().body("you have to give your price");
             }
             //appserv.affecterOfferApplication(ido,supp.getIdApplication());
             supp.setOffer(off);
              appserv.add(supp);
             return  ResponseEntity.ok(supp);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving application");
         }
     }

     @PutMapping("/updateApplication/{idap}/{ido}")
    public ResponseEntity<?> updateApplication(@RequestBody SupplierApplication supp,@PathVariable Long idap,@PathVariable Long ido)
     {
         Offer off=offerserv.retrieveById(ido);
         try{
             if(off == null){
                 return ResponseEntity.badRequest().body("offer does not exist");
             }
             if(supp.getPriceGiven() == 0){
                 return ResponseEntity.badRequest().body("you have to give your price");
             }
             //appserv.affecterOfferApplication(ido,supp.getIdApplication());
             SupplierApplication app =appserv.retrieveById(idap);
             app.setOffer(off);
             app.setPriceGiven(supp.getPriceGiven());
             app.setSupplier(supp.getSupplier());
              appserv.add(app);
             return  ResponseEntity.ok(app);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving application");
         }
     }
    @GetMapping("/getapplications")
    public List<SupplierApplication> getAllApplications()
    {
        return appserv.retrieveAll();
    }

    @GetMapping("/getapplication/{Id}")
    public SupplierApplication getApplication(@PathVariable Long Id)
    {
        return appserv.retrieveById(Id);
    }

    @DeleteMapping("/delapplication/{Id}")
    public  void deleteApplication(@PathVariable Long Id)
    {
        appserv.delete(Id);
    }
}
