package com.example.backend.Controller;

import com.example.backend.Entity.AnalysisCenter;
import com.example.backend.Entity.Appointment;
import com.example.backend.Playload.Request.RegisterRequest;
import com.example.backend.Playload.Response.AuthentificationResponse;
import com.example.backend.Services.AppointmentService;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rendezvous")
@AllArgsConstructor
public class AppointementController extends GenericController<Appointment,Long> {


    AppointmentService appointmentService;

    @PostMapping("/addapt/{idUser}")
    public void register (@RequestBody  Appointment  appointment, @PathVariable("idUser") Long id)
    {
        appointmentService.AddapntetAfectUserMedecin(appointment,id);





    }
}
