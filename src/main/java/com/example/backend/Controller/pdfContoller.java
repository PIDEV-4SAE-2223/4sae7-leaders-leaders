package com.example.backend.Controller;


import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.shiftRepo;
import com.example.backend.Services.InternService;
import com.example.backend.Services.InternshipService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.WebContext;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class pdfContoller {

    InternService internserv;
    shiftRepo shiftrep;
    internRepo intenr;

    @RequestMapping(path = "/")
    public List<Shift> getOrderPage(Model model,@PathVariable Long id) throws IOException {

        List<Shift> shifts =shiftrep.findByInternId(id);
        model.addAttribute("shifts", shifts);
        return shifts;
    }
    @RequestMapping(path = "/pdf")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response,@PathVariable Long id) throws IOException {

        /* Do Business Logic*/

        Intern i =intenr.findById(id).orElse(null);

        /* Create HTML using Thymeleaf template Engine */


        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }
}
