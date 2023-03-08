package com.example.backend.Services;

import com.example.backend.Entity.InternshipRequest;
import org.springframework.http.ResponseEntity;

public interface InternshipService extends IService<InternshipRequest> {

    ResponseEntity<?> affecterInternOnternship(Long IdInternship, Long IdIntern);
    ResponseEntity<?> accecptInternship(Long idi);
    ResponseEntity<?> refuseInternship(Long idi);
}
