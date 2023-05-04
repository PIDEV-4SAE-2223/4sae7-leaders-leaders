package com.example.backend.Services;

import com.example.backend.Entity.Appointment;
import com.example.backend.Entity.AppointmentStatus;
import com.example.backend.Entity.UploadFile;
import com.example.backend.Playload.Response.MessageResponse;
import com.example.backend.Repository.AppointmentRepository;
import com.example.backend.Repository.UploadFileRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Service
@AllArgsConstructor
public class UploadFileService {
    private   AppointmentRepository appointmentRepository;
    private   UploadFileRepository uploadFileRepository;
    private AnalysisTypeService analyse;
    private static final Logger logger = LoggerFactory.getLogger(UploadFileService.class);

    private final Path root = Paths.get("UploadFile");
    public MessageResponse uploadFile(List<MultipartFile> files, Long idAppointement){

        Appointment appointment = appointmentRepository.findById(idAppointement).orElse(null);
        logger.debug("RegisterRequest object: {}", appointment.toString());
        logger.debug("User object before saving to the database: {}", appointment);

        try {
            for (MultipartFile file: files) {
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                UploadFile uploadFile = new UploadFile();
                uploadFile.setAppointment(appointment);
                uploadFile.setPath(file.getOriginalFilename());
                uploadFile.setFileBlob(file.getBytes());
                uploadFileRepository.save(uploadFile);
                  if(analyse.isAnalysisDangerous(uploadFile.getFileBlob())){
                    appointment.setStatus(AppointmentStatus.URGENT);
                    appointmentRepository.save(appointment);
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
            return new MessageResponse(false, "Attention", "Operation not effectuated");
        }

        return  new MessageResponse(true, "Success", "Operation not effectuated");

    }

}
