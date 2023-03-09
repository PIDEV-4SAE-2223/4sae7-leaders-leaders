package com.example.backend.Services;
import com.example.backend.Entity.Equiipment;
import com.example.backend.Entity.Notification;
import com.example.backend.Entity.Offfer;
import com.example.backend.Entity.Status;
import com.example.backend.Repository.EquipmentRepository;
import com.example.backend.Repository.NotifictionRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor

public class EquipmentServiceImpl implements EquipmentService {

    private EquipmentRepository equipmentRepository;
    private NotifictionRepository notifictionRepository;

    @Override
    public Equiipment findById(Long id) {return equipmentRepository.findById(id).orElse(null);}

    @Override
    public List<Equiipment> findAll() {
        return  equipmentRepository.findAll();
    }

    @Override
    public Equiipment saveEquipement(Equiipment equipemet) throws Exception {
        if(equipemet.getDateEndUsefullLife().after(new Date()))
        {
            equipemet.setStatus(Status.Available);
            return equipmentRepository.save(equipemet);
        }
        throw new Exception("the date must be in the future ") ;
    }

    @Override
    public void deleteById(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Override
    public Equiipment updateEquipment(Long id, Equiipment equipment) {
        Equiipment eq= equipmentRepository.findById(id).get();
        eq.setNameequipment(equipment.getNameequipment());
        eq.setFavorite(equipment.isFavorite());
        eq.setDateEndUsefullLife((equipment.getDateEndUsefullLife()));
        eq.setDescription(equipment.getDescription());
        eq.setStatus(equipment.getStatus());
        eq.setPicture(equipment.getPicture());
        eq.setOffer(equipment.getOffer());
        return equipmentRepository.save(eq);

    }

    @Override
    public Equiipment isFavorite(Long id) {
        Equiipment eq = equipmentRepository.findById(id).orElse(null);
        if (eq == null) {
            throw new RuntimeException("equipement does not exist");
        } else if (eq.isFavorite()) {
            throw new RuntimeException("equipement is alrady in favorite list");
        } else{ eq.setFavorite(true);}

        return equipmentRepository.save(eq);
    }

    @Override
    public List<Equiipment> ListFavorite() {
            return equipmentRepository.findByFavoriteIsTrue();
    }

    @Scheduled(cron = "0 2 9 3 ?") // Run at midnight on the first day of every month
    public void notifyExpiringEquipment() {
        Date threeMonthsFromNow = DateUtils.addMonths(new Date(), 3);
        List<Equiipment> equipments = equipmentRepository.findAll();
        for (Equiipment equipment : equipments) {
            {
                if (equipment.getStatus() == Status.Available && equipment.getDateEndUsefullLife().after(threeMonthsFromNow)  ) {
                    equipment.setStatus(Status.WillBeExpired);
                    equipmentRepository.save(equipment);
                    Notification newNotification= new Notification();
                    newNotification.setMessage("Equipment " + equipment.getIdEquipment() + "  will be expired " + equipment.getDateEndUsefullLife());
                    newNotification.setCreatedAt(new Date(System.currentTimeMillis()));
                    newNotification.setEquipment(equipment);
                    newNotification.setStatus(Status.WillBeExpired);
                    notifictionRepository.save(newNotification);
                } else if (equipment.getStatus() == Status.WillBeExpired && equipment.getDateEndUsefullLife().after(new Date())   ) {
                    equipment.setStatus(Status.Expired);
                    equipmentRepository.save(equipment);
                    Notification newNotification= new Notification();
                    newNotification.setMessage("Equipment " + equipment.getIdEquipment() + "  expired " + equipment.getDateEndUsefullLife());
                    newNotification.setCreatedAt(new Date(System.currentTimeMillis()));
                    newNotification.setEquipment(equipment);
                    newNotification.setStatus(Status.Expired);
                    notifictionRepository.save(newNotification);
                }
            }
        }
    }

    public List<Equiipment> findAllEquipmentWithOffer() {
        return equipmentRepository.findAllWithOffer();
    }
}






