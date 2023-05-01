package com.example.backend.Services;

import com.example.backend.Entity.Certificat;
import com.example.backend.Entity.Formation;
import com.example.backend.Entity.Image;
import com.example.backend.Entity.Offer;
import com.example.backend.Repository.CertificatRepository;
import com.example.backend.Repository.FormationRepository;
import com.example.backend.Repository.ImageDbRepository;
import com.example.backend.Util.ImageUtil;
import com.example.backend.generic.IGenericServiceImp;
import com.example.backend.qrcode.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

@Service
@AllArgsConstructor
public class CertificatService extends IGenericServiceImp<Certificat,Long> implements ICertificatService{
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/";
    private final CertificatRepository certificatRepository;

    private final FormationRepository formationRepository;

    private  final ImageDbRepository imageRepository;
    private final InFormationService  formationService;


    @Override
    public String createQrCode(Model model, Long idFormation) {
        Formation formation = formationService.retrieveById(idFormation);
        Certificat certification = formation.getCertificat();
        if (certification!=null ) {
            String title = certification.getName();
            String descriptionCertificat = certification.getDescription();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateCertificatAsString = dateFormat.format(certification.getDate());
            String idQrCode = String.valueOf(certification.getId());
            byte[] image = new byte[0];
            String pathImageQrCode = QR_CODE_IMAGE_PATH.concat("Qrcode-").concat(idQrCode).concat(".png");
            try {
                image = QRCodeGenerator.getQRCodeImage(title, 350, 350);
                String detail = "Certification de participation \n".concat(title).concat("\n ").concat(descriptionCertificat).concat("\n Date: ").concat(dateCertificatAsString);
                QRCodeGenerator.generateQRCodeImage(detail, 350, 350, pathImageQrCode);

                //save image qrcode in dataBase
                Image x = null;
                x = Image.builder()
                        .name("Qrcode-"+idQrCode+".png")
                        .type("image/jpeg")
                        .imageData(ImageUtil.compressImage(image)).build();
                imageRepository.save(x);
                certification.setImg(x);
                certification.setPathQrcode(pathImageQrCode);

                certificatRepository.save(certification);
                //end

            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }
            String qrcode = Base64.getEncoder().encodeToString(image);
            model.addAttribute("title", title);
            model.addAttribute("Date", dateCertificatAsString);
            model.addAttribute("Description", descriptionCertificat);
            model.addAttribute("qrcode", qrcode);
            return pathImageQrCode;

        }
        else
            return null;


    }



    @Override
    public ResponseEntity<?> addCertificationToFormation(Model model, Long idFormation, Certificat certificat) throws Exception{

    Formation formation = formationService.retrieveById(idFormation);
        if (formation == null) {
        return ResponseEntity.ok("Error: Training not found!");
    }
        try {
        Certificat getCertification = certificatRepository.save(certificat);
        certificatRepository.save(getCertification);
        formation.setId(idFormation);
        formation.setCertificat(getCertification);
        formationRepository.save(formation);
        String qrCodePath = createQrCode(model, formation.getId());
        getCertification.setPathQrcode(qrCodePath);
        certificatRepository.save(getCertification);
        return ResponseEntity.ok(getCertification);
    }catch (Exception ex){
        throw new Exception(ex.getMessage());
    }
    }


}
