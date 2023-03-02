package com.example.backend.Repository;

import com.example.backend.Entity.Certificat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificatRepository extends JpaRepository<Certificat, Long> {
}