package com.example.backend.Services;

import com.example.backend.Entity.Equipment;
import com.example.backend.Repository.equipementRepository;
import com.example.backend.generic.IGenericServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipementService extends IGenericServiceImp<Equipment,Long> implements IEquipementService{


    private final equipementRepository equiprepo;

    @Override
    public Equipment isFavorite(Long id) {
        Equipment eq=equiprepo.findById(id).orElse(null);
        eq.setFavorite(true);
        return equiprepo.save(eq);
    }

    @Override
    public List<Equipment> ListFavorite() {
        return equiprepo.findByFavoriteIsTrue();
    }
}
