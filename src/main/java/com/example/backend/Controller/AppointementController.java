package com.example.backend.Controller;

import com.example.backend.Entity.AnalysisCenter;
import com.example.backend.Entity.Appointment;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rendezvous")
@AllArgsConstructor
public class AppointementController extends GenericController<Appointment,Long> {
}
