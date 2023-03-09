package com.example.backend.Repository;

import com.example.backend.Entity.Equiipment;
import com.example.backend.Entity.Offfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equiipment,Long> {
    List<Equiipment> findByFavoriteIsTrue();
    @Query("SELECT e FROM Equiipment e JOIN FETCH e.offer o")
    List<Equiipment> findAllWithOffer();
}
