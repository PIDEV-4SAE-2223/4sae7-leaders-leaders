package com.example.backend.Controller;


import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Services.IService;
import com.example.backend.Services.InternService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/intern")
public class internController {

    public InternService internService;

    @PostMapping("/addIntern")
    public Intern addIntern(@RequestBody Intern i)
    {
        return internService.create(i);
    }

   /* @PutMapping("/affectShiftuser/{ids}/{idi}")
    public Intern affectShiftUser(@PathVariable Long ids,@PathVariable Long idi){
        internService.affecterShiftIntern(ids,idi);
        return internService.findById(idi);
    }
    @PutMapping("/affectShiftIntern/{ids}/{idi}")
    public Shift affectShiftIntern(@PathVariable Long ids, @PathVariable Long idi){

        return internService.findById(idi);
    }*/

    @PutMapping("/updateIntern")//we have to change the selct arg by cin passed in the request
    public List<Intern> updateIntern(@RequestBody Intern intern)
    {
        Intern i=new Intern();
        i.setFirstName(intern.getFirstName());
        i.setLastName(intern.getLastName());
        i.setEmail(intern.getEmail());
        i.setInternshipRequests(intern.getInternshipRequests());
        internService.deleteById(intern.getCin());// check if it's necessery
         internService.create(i);
        return internService.findAll();
    }

    @GetMapping("/getInterns")
    public List<Intern> getAllInterns()
    {
        return internService.findAll();
    }


    @GetMapping("/getIntern/{Id}")
    public Intern getIntern(@PathVariable Long Id)
    {
        return internService.findById(Id);
    }

    @DeleteMapping("/delIntern/{Id}")
    public List<Intern> deleteIntern(@PathVariable Long Id)
    {
        return internService.deleteById(Id);
    }
}
