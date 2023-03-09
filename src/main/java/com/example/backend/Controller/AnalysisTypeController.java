package com.example.backend.Controller;

import com.example.backend.Entity.AnalysisCenter;
import com.example.backend.Entity.AnalysisType;
import com.example.backend.Entity.UploadFile;
import com.example.backend.Repository.UploadFileRepository;
import com.example.backend.Services.AnalysisTypeService;
import com.example.backend.generic.GenericController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/types")
@AllArgsConstructor
public class AnalysisTypeController extends GenericController<AnalysisType,Long> {

    private final AnalysisTypeService analyse;
    private final UploadFileRepository uprep;
    @GetMapping("/idDanger/{id}")
    public boolean isdanger(@PathVariable Long id){
        UploadFile fileBlob=uprep.findById(id).orElse(null);
        return analyse.isAnalysisDangerous(fileBlob.getFileBlob());
    }
}
