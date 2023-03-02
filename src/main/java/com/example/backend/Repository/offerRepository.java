package com.example.backend.Repository;

import com.example.backend.Entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface offerRepository extends JpaRepository<Offer,Long> {
    List<Offer> findByArchiveIsTrue();
    List<Offer> findByArchiveIsFalse();
}
