package com.example.backend.Services;

import com.example.backend.Entity.Certificat;
import com.example.backend.Entity.Formation;
import com.example.backend.Repository.CertificatRepository;
import com.example.backend.Repository.FormationRepository;
import com.example.backend.qrcode.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

@Service
@AllArgsConstructor
public class CertificatService {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/";
    @Autowired
    private CertificatRepository certificatRepository;

    @Autowired
    private FormationRepository formationRepository;

    public String createQrCode(Model model, Long idFormation) {
        Formation formation = formationRepository.findByIdFormation(idFormation);
        Certificat certification = formation.getCertificat();
        String title = certification.getName();
        String descriptionCertificat = certification.getDescription();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateCertificatAsString = dateFormat.format(certification.getDate());
        String idQrCode = String.valueOf(certification.getId());
        byte[] image = new byte[0];
        String pathImageQrCode = QR_CODE_IMAGE_PATH.concat("Qrcode-").concat(idQrCode).concat(".png");
        try {
            image = QRCodeGenerator.getQRCodeImage(title,350,350);
            String detail = "Certification de participation \n".concat(title).concat("\n ").concat(descriptionCertificat).concat("\n Date: ").concat(dateCertificatAsString);
            QRCodeGenerator.generateQRCodeImage(detail,350,350, pathImageQrCode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String qrcode = Base64.getEncoder().encodeToString(image);
        model.addAttribute("title",title);
        model.addAttribute("Date", dateCertificatAsString);
        model.addAttribute("Description", descriptionCertificat);
        model.addAttribute("qrcode",qrcode);
        return pathImageQrCode;
    }
}
