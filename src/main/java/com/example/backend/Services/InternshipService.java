package com.example.backend.Services;

import com.example.backend.Entity.InternshipRequest;

public interface InternshipService extends IService<InternshipRequest> {

    void affecterInternOnternship(Long IdInternship, Long IdIntern);
    InternshipRequest accecptInternship(InternshipRequest internship);
}
