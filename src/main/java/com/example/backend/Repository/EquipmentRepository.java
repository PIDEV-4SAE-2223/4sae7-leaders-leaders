package com.example.backend.Repository;

import com.example.backend.Entity.Equiipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equiipment,Long> {
    List<Equiipment> findByFavoriteIsTrue();

}
