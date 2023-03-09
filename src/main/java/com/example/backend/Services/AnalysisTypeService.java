package com.example.backend.Services;

import com.example.backend.Entity.AnalysisType;
import com.example.backend.Entity.DangerousAnalysisType;
import com.example.backend.Entity.User;
import com.example.backend.Repository.AnalysisTypeRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AnalysisTypeService extends IGenericServiceImp<AnalysisType,Long> implements IAnalysisTypeInterface {
    private final AnalysisTypeRepository analysisTypeRepository;



    public AnalysisType createAnalysisType(String title, String description, Date analysisDelay) {
        AnalysisType analysisType = new AnalysisType();
        analysisType.setTitle(title);
        analysisType.setDescription(description);
        analysisType.setAnalysisDelay(analysisDelay);
        return analysisTypeRepository.save(analysisType);
    }


    public boolean isAnalysisDangerous(byte[] fileBlob) {
        // Convert PDF file to text
        String text = convertPdfToText(fileBlob);
        String[] words = text.split("\\s+");

        // Check if text contains dangerous analysis types
        for (String word:words) {
            if (DangerousAnalysisType.CANCER.name().equalsIgnoreCase(word)) {
                return true;
            }
            if (DangerousAnalysisType.HIV.name().equalsIgnoreCase(word)) {
                return true;
            }
            if (DangerousAnalysisType.HEPATITIS_B.name().equalsIgnoreCase(word)) {
                return true;
            }
            if (DangerousAnalysisType.HEPATITIS_C.name().equalsIgnoreCase(word)) {
                return true;
            }
            if (DangerousAnalysisType.EBOLA.name().equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private String convertPdfToText(byte[] fileBlob) {
        try (PDDocument document = PDDocument.load(fileBlob)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Error converting PDF to text", e);
        }
    }
}
