package com.example.backend.Repository;

import com.example.backend.Entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface equipementRepository extends JpaRepository<Equipment,Long> {

    List<Equipment> findByFavoriteIsTrue();
}
