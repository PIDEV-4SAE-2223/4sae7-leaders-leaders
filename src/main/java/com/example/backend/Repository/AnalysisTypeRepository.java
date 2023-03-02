package com.example.backend.Repository;

import com.example.backend.Entity.AnalysisType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisTypeRepository extends JpaRepository<AnalysisType, Long> {
}