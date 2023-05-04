package com.example.backend.Services;

import com.example.backend.Entity.Appointment;
import com.example.backend.Entity.AppointmentStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAppointmentInterface {
    List<Appointment> getAppointmentsByStatus();
    ResponseEntity<?> AddapntetAfectType(Appointment appointment, Long idt, Long idu);
}
