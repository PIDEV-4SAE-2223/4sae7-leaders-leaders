package com.example.backend.Controller;

import com.example.backend.Entity.AnalysisCenter;
import com.example.backend.Entity.MedicalReport;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class MedicalReportController extends GenericController<MedicalReport,Long> {
}
