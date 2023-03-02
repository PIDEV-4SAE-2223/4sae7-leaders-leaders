package com.example.backend.Services;

import com.example.backend.Entity.Appointment;
import com.example.backend.Entity.User;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentService extends IGenericServiceImp<Appointment,Long> implements IAppointmentInterface{
}
