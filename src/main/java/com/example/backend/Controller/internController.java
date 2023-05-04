package com.example.backend.Controller;


import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.internshipRequestRepo;
import com.example.backend.Services.IService;
import com.example.backend.Services.InternService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/intern")
public class internController {

    public InternService internService;

    @PostMapping("/addIntern")
    public ResponseEntity<?> addIntern(@RequestBody Intern i)
    {
        return internService.create(i);
    }

    @PutMapping("/updateIntern/{id}")//we have to change the selct arg by cin passed in the request
    public ResponseEntity<?> updateIntern(@RequestBody Intern intern,@PathVariable Long id)
    {
       return internService.update(intern,id);
    }

    @GetMapping("/getInterns")
    public ResponseEntity<?> getAllInterns()
    {
        return internService.findAll();
    }


    @GetMapping("/getIntern/{Id}")
    public ResponseEntity<?> getIntern(@PathVariable Long Id)
    {
        return internService.findById(Id);
    }

    @DeleteMapping("/delIntern/{Id}")
    public List<Intern> deleteIntern(@PathVariable Long Id)
    {
        return internService.deleteById(Id);
    }
}
