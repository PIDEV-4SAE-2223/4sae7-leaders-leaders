package com.example.backend.Repository;

import com.example.backend.Entity.Offfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OffferRepository extends JpaRepository<Offfer , Long> {
    List<Offfer> findByArchiveIsTrue();
    List<Offfer> findByArchiveIsFalse();
}
