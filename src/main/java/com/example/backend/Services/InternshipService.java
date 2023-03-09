package com.example.backend.Services;

import com.example.backend.Entity.InternshipRequest;
import com.example.backend.Entity.Status;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface InternshipService extends IService<InternshipRequest> {

    ResponseEntity<?> affecterInternOnternship(Long IdInternship, Long IdIntern);
    ResponseEntity<?> accecptInternship(Long idi);
    ResponseEntity<?> refuseInternship(Long idi);
    Map<Status, Long> countByStatus();
    List<Object[]> countbyDepartement();
}
