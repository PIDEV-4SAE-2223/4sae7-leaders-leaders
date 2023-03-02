package com.example.backend.Services;

import com.example.backend.Entity.MedicalReport;
import com.example.backend.Entity.User;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedicalReportService extends IGenericServiceImp<MedicalReport,Long> implements IMedicalReportInterface {
}
