package com.example.backend.Services;


import com.example.backend.Entity.Equiipment;


import java.util.List;

public interface EquipmentService {

    public Equiipment findById(Long id);
    public List<Equiipment> findAll();
    public Equiipment saveEquipement(Equiipment equipemet) throws Exception;
    public void deleteById(Long id);
    public Equiipment updateEquipment(Long id,Equiipment equipment);
    public Equiipment isFavorite(Long id) ;
    public List<Equiipment> ListFavorite();

}
