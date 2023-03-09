package com.example.backend.Repository;

import com.example.backend.Entity.AnalysisType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalysisTypeRepository extends JpaRepository<AnalysisType, Long> {
   List<AnalysisType>  findByTitleContainingIgnoreCase(String title);
   List<AnalysisType> findByIsDangerous(boolean isDangerous);
}