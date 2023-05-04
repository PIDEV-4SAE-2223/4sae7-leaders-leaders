package com.example.backend.Controller;

import com.example.backend.Entity.AnalysisCenter;
import com.example.backend.Entity.Appointment;
import com.example.backend.Entity.AppointmentStatus;
import com.example.backend.Entity.AppointmentStatusCount;
import com.example.backend.Playload.Request.RegisterRequest;
import com.example.backend.Playload.Response.AuthentificationResponse;
import com.example.backend.Playload.Response.MessageResponse;
import com.example.backend.Repository.AppointmentRepository;
import com.example.backend.Services.*;
import com.example.backend.generic.GenericController;
import io.jsonwebtoken.io.IOException;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;


@RestController
@RequestMapping("/rendezvous")
@AllArgsConstructor

public class AppointementController extends GenericController<Appointment,Long> {

    private final IAppointmentInterface appint;
   private final AppointmentService appointmentService;
    private final UploadFileService iServiceAttachment;

    @PostMapping("/addapt/{idt}/{idu}")
    public ResponseEntity<?> register(@RequestBody Appointment appointment,@PathVariable Long idt,@PathVariable Long idu) {
        return appint.AddapntetAfectType(appointment,idt,idu);
    }

    @PostMapping("/uploadFile/{id}")
    public   ResponseEntity<MessageResponse>  upload(@RequestParam("files") List<MultipartFile> files, @PathVariable Long id) {
        MessageResponse message =    iServiceAttachment.uploadFile(files,id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping("/urgentApp")
    public List<Appointment> urgent()
    {
        return appint.getAppointmentsByStatus();
    }

    @GetMapping("/statusCount")
    public List<AppointmentStatusCount> getAppointmentCountByStatus() {
        return appointmentService.getAppointmentCountByStatus();
    }

}

