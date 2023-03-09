package com.example.backend.Services;

import com.example.backend.Entity.Appointment;
import com.example.backend.Entity.User;
import com.example.backend.Repository.AppointmentRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class AppointmentService extends IGenericServiceImp<Appointment,Long> implements IAppointmentInterface{



    UserRepository userRepository;

    AppointmentRepository  appointmentRepository;

@Transactional
public void AddapntetAfectUserMedecin(Appointment appointment,Long id){
        User user=userRepository.findById(id).orElse(null);
        appointment.setUser(user);
        appointmentRepository.save(appointment);
        }
        }
