package com.example.backend.Services;


import com.example.backend.Entity.Offer;
import com.example.backend.Entity.SupplierApplication;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.applicationRepository;
import com.example.backend.Repository.offerRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationService extends IGenericServiceImp<SupplierApplication,Long> implements IApplicationService {

    private final applicationRepository apprepo;
    private final UserRepository userrepo;
    private final offerRepository offerepo;

    @Override
    public SupplierApplication affecterSupplierApplication(Long ids, Long ida) {
        SupplierApplication app=apprepo.findById(ida).orElse(null);
        User user=userrepo.findById(ids).orElse(null);
        app.setSupplier(user);
        return apprepo.save(app);
    }

    @Override
    public SupplierApplication affecterOfferApplication(Long ido, Long ida) {
        SupplierApplication app=apprepo.findById(ida).orElse(null);
        Offer off=offerepo.findById(ido).orElse(null);
        app.setOffer(off);
        return apprepo.save(app);
    }


}
