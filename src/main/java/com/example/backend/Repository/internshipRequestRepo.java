package com.example.backend.Repository;

import com.example.backend.Entity.InternshipRequest;
import com.example.backend.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface internshipRequestRepo extends JpaRepository<InternshipRequest,Long> {
    @Query("SELECT ir.status, COUNT(ir) FROM InternshipRequest ir GROUP BY ir.status")
    List<Object[]> countByStatus();

    default Map<Status, Long> countByStatusMap() {
        List<Object[]> results = countByStatus();
        Map<Status, Long> statistics = new HashMap<>();
        for (Object[] result : results) {
            statistics.put((Status) result[0], (Long) result[1]);
        }
        return statistics;
    }

    @Query("SELECT ir.departement, COUNT(ir) FROM InternshipRequest ir WHERE ir.status = :status GROUP BY ir.departement")
    List<Object[]> countByDepartmentAndStatus(@Param("status") Status status);
}
