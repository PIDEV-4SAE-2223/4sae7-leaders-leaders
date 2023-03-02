package com.example.backend.Controller;

import com.example.backend.Entity.Equipment;
import com.example.backend.Entity.Intern;
import com.example.backend.Entity.User;
import com.example.backend.Services.EquipementService;
import com.example.backend.Services.IEquipementService;
import com.example.backend.generic.GenericController;
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
@RequestMapping("/equipement")
public class equipementController {

     private final IEquipementService iequipement;



     @PostMapping("/addequipement")
     public ResponseEntity<?> addIntern(@RequestBody Equipment equipment)
     {
          try{
               LocalDate now =  LocalDate.now();//now Type localdate
               Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
               if(equipment.getDateManufacture().after(datt)){
                    return ResponseEntity.badRequest().body("Manufacture date cannot be in the future");
               }
               if (equipment.getDateEndUsefullLife().before(datt)) {
                    return ResponseEntity.badRequest().body("End of useful life date cannot be in the past");
               }
          Equipment eq = iequipement.add(equipment);
               return  ResponseEntity.ok(eq);
          } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving equipment");
          }
     }

     @PutMapping("/updatequipment/{id}")//we have to change the selct arg by cin passed in the request
     public ResponseEntity<?> updateIntern(@RequestBody Equipment equipment, @PathVariable Long id)
     {
          try{
               LocalDate now =  LocalDate.now();//now Type localdate
               Date datt=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());//parsing now to date
               if(equipment.getDateManufacture().after(datt)){
                    return ResponseEntity.badRequest().body("Manufacture date cannot be in the future");
               }
               if (equipment.getDateEndUsefullLife().before(datt)) {
                    return ResponseEntity.badRequest().body("End of useful life date cannot be in the past");
               }
          Equipment eq=iequipement.retrieveById(id);
          eq.setName(equipment.getName());
          eq.setFavorite(equipment.isFavorite());
          eq.setDescription(equipment.getDescription());
          eq.setOffer(equipment.getOffer());
          eq.setQuantity(equipment.getQuantity());
          eq.setDateManufacture(equipment.getDateManufacture());
          eq.setDateEndUsefullLife(equipment.getDateEndUsefullLife());
          Equipment equip= iequipement.add(eq);
               return  ResponseEntity.ok(equip);
          } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving equipment");
          }
     }
     @GetMapping("/getequipments")
     public List<Equipment> getAllInterns()
     {
          return iequipement.retrieveAll();
     }

     @GetMapping("/getequipment/{Id}")
     public Equipment getIntern(@PathVariable Long Id)
     {
          return iequipement.retrieveById(Id);
     }

     @DeleteMapping("/delequipment/{Id}")
     public  void deleteIntern(@PathVariable Long Id)
     {
           iequipement.delete(Id);
     }

     @PutMapping("/isfavorite/{id}")
     public ResponseEntity<?> addToFavorite(@PathVariable Long id){
            Equipment equipe=iequipement.isFavorite(id);
          try{
               if(equipe == null){
                    return ResponseEntity.badRequest().body("equipement does not exist");
               }
               return  ResponseEntity.ok(equipe);
          } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding equipment to favorite");
          }
     }

     @GetMapping("/listFavorite")
     public List<Equipment> getListFavorite(){
          return iequipement.ListFavorite();
     }
}
