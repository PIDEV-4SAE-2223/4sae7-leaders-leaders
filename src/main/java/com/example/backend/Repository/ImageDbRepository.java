package com.example.backend.Repository;
import com.example.backend.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDbRepository extends JpaRepository<Image, Long> {
    Image findByName(String name);
    Image findById(long id);
}

