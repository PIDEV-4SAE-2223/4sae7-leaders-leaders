package com.example.backend.Services;

import com.example.backend.Entity.Intern;
import com.example.backend.Entity.Shift;
import com.example.backend.Repository.internRepo;
import com.example.backend.Repository.shiftRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class serviceIntern implements InternService {

    private final internRepo internrepo;
    private final shiftRepo shiftrepo;


    @Override
    public Intern create(Intern saved) {

        return internrepo.save(saved);

    }


    @Override
    public Intern findById(Long id) {
       // Long cin=id.getCin();
        return internrepo.findById(id).orElse(null);
    }

    @Override
    public List<Intern> findAll() {
        return internrepo.findAll();
    }

    @Override
    public List<Intern> deleteById(Long Id) {
        internrepo.deleteById(Id);
        return internrepo.findAll();
    }

   /* @Override
    public void affecterShiftIntern(Long idShift,Long IdIntern) {
        Shift shiftIn=shiftrepo.findById(idShift).orElse(null);
        Intern internShift=internrepo.findById(IdIntern).orElse(null);
        internShift.setShift(shiftIn);
        internrepo.save(internShift);
    }*/
}
