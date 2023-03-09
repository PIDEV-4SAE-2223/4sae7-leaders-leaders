package com.example.backend.Controller;

import com.example.backend.Entity.Equiipment;
import com.example.backend.Entity.Offfer;
import com.example.backend.Services.OffferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Offfer")
@AllArgsConstructor
public class OffferController {

    private OffferService offferService;


    @PostMapping("/addOfffer/{idEquipment}")
    public Offfer saveAndaddOfferToEquipment(@RequestBody Offfer offfer, @PathVariable Long idEquipment) {
       return offferService.saveAndaddOfferToEquipment(offfer, idEquipment) ;
    }
    @GetMapping("/getOfffer")
    public List<Offfer> getAllOfffer() {
        return offferService.findAll();
    }
    @GetMapping("/getOfffer/{id}")
    public Offfer getOfffer(@PathVariable Long id) {
        return offferService.findById(id);
    }
    @DeleteMapping("/delOfffer/{Id}")
    public void deleteOfffer(@PathVariable Long Id) {
        offferService.deleteById(Id);
    }
    @PutMapping("/updateOfffer/{id}")
    public Offfer updateOfffer( @PathVariable Long id,@RequestBody Offfer offfer) {
        return offferService.updateOfffer(id, offfer);
    }






}
