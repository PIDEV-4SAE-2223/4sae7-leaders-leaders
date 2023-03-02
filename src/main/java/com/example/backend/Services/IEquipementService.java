package com.example.backend.Services;

import com.example.backend.Entity.Equipment;
import com.example.backend.generic.IGenericService;

import java.util.List;

public interface IEquipementService extends IGenericService<Equipment,Long> {

    Equipment isFavorite(Long id);
    List<Equipment> ListFavorite();
}
