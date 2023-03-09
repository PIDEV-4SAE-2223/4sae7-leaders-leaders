package com.example.backend.Controller;


import com.example.backend.Entity.Equiipment;
import com.example.backend.Services.EquipmentService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/equipement")

public class EquipmentController {


    private EquipmentService equipmentService;

    @PostMapping("/addequipement")
    public Equiipment addEquipement(@RequestBody Equiipment equipment) throws Exception {
        return equipmentService.saveEquipement(equipment);
    }
    @GetMapping("/getequipments")
    public List<Equiipment> getAllEquipemet() {
        return equipmentService.findAll();
    }

    @GetMapping("/getequipment/{id}")
    public Equiipment getEquipment(@PathVariable Long id) {
        return equipmentService.findById(id);
    }

    @DeleteMapping("/delequipment/{Id}")
    public void deleteEquipment(@PathVariable Long Id) {
        equipmentService.deleteById(Id);
    }

    @PutMapping("/updatequipment/{id}")//we have to change the select arg by cin passed i wvgfn the request
    public Equiipment updateEquipement( @PathVariable Long id,@RequestBody Equiipment equipment) {
        return equipmentService.updateEquipment(id, equipment);
    }

    @PutMapping("/isfavorite/{id}")
    public Equiipment addToFavorite(@PathVariable Long id){ return equipmentService.isFavorite(id);}
    @GetMapping("/listFavorite")
    public List<Equiipment> getListFavorite(){
        return equipmentService.ListFavorite();
    }

    @GetMapping("/getOfferwithequipment")
    public List<Equiipment> getAllEquipmentWithOffer() {
        return equipmentService.findAllEquipmentWithOffer();
    }

}
