package com.example.backend.Controller;

import com.example.backend.Entity.AnalysisCenter;
import com.example.backend.Entity.AnalysisType;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/types")
@AllArgsConstructor
public class AnalysisTypeController extends GenericController<AnalysisType,Long> {
}
