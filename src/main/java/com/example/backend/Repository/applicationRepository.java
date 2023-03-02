package com.example.backend.Repository;

import com.example.backend.Entity.SupplierApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface applicationRepository extends JpaRepository<SupplierApplication,Long> {
    List<SupplierApplication> findAllByOrderByPriceGivenAsc();
}
