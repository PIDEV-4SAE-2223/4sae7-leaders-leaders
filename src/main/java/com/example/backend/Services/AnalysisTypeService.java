package com.example.backend.Services;

import com.example.backend.Entity.AnalysisType;
import com.example.backend.Entity.User;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalysisTypeService extends IGenericServiceImp<AnalysisType,Long> implements IAnalysisTypeInterface {
}
