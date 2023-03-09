package com.example.backend.Controller;

import com.example.backend.Entity.Offfer;
import com.example.backend.Services.OffferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/Offfer")
@AllArgsConstructor
public class OffferController {

    private OffferService offferService;


    @PostMapping("/addOfffer/{idEquipment}")
    public Offfer saveAndaddOfferToEquipment(@RequestBody Offfer offfer, @PathVariable Long idEquipment) {
       return offferService.saveAndaddOfferToEquipment(offfer, idEquipment) ;
    }



}
