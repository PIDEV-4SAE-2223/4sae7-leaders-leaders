package com.example.backend.Services;

import com.example.backend.Entity.*;
import com.example.backend.Playload.Response.MessageResponse;
import com.example.backend.Repository.AnalysisTypeRepository;
import com.example.backend.Repository.AppointmentRepository;
import com.example.backend.Repository.UploadFileRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AppointmentService extends IGenericServiceImp<Appointment, Long> implements IAppointmentInterface {


    UserRepository userRepository;
    AnalysisTypeRepository anal;
    AppointmentRepository appointmentRepository;
    UploadFileService iServiceAttachment ;
    List<MultipartFile> files ;


    private UploadFileRepository uploadFileRepository;



    @Transactional
    public void AddapntetAfectUserMedecin(Appointment appointment,Long id) {
       User user = userRepository.findById(id).orElse(null);
       appointment.setUser(user);
        // iServiceAttachment.uploadFile(files,appointment.getIdAppointement());
        appointmentRepository.save(appointment);


    }

    @Override
    public ResponseEntity<?> AddapntetAfectType(Appointment appointment, Long idt,Long idu) {
        try{
        AnalysisType type = anal.findById(idt).orElse(null);
        appointment.setType(type);
            User user = userRepository.findById(idu).orElse(null);
            appointment.setUser(user);
            if(user.getAge() < 18 && type.getTitle().equals("analyse maternite")){
                return ResponseEntity.badRequest().body("you have to call the police");
            }
            if(type.getTitle().equalsIgnoreCase("analyse maternite") && user.getSexe() == Sexe.HOMME){
                return ResponseEntity.badRequest().body("3andek gaz bara rawah");
            }
            appointmentRepository.save(appointment);
            return  ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving appointement");
        }

    }


    @Override
    @Scheduled(cron = "0 * * * * *")
    public List<Appointment> getAppointmentsByStatus() {
        List<Appointment> appointments=appointmentRepository.findByStatus(AppointmentStatus.URGENT);
        System.out.println("Appointments with URGENT status: " + appointments);
        return appointments;
    }

    public List<AppointmentStatusCount> getAppointmentCountByStatus() {
        List<Object[]> result = appointmentRepository.countAppointmentsByStatus();
        List<AppointmentStatusCount> countByStatus = new ArrayList<>();
        for (Object[] obj : result) {
            AppointmentStatus status = (AppointmentStatus) obj[0];
            Long count = (Long) obj[1];
            countByStatus.add(new AppointmentStatusCount(status, count));
        }
        return countByStatus;
    }



}




