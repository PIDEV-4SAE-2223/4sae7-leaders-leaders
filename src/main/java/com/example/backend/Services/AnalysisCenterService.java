package com.example.backend.Services;

import com.example.backend.Entity.AnalysisCenter;
import com.example.backend.Entity.User;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalysisCenterService extends IGenericServiceImp<AnalysisCenter,Long> implements IAnalysisCenterInterface {
}
