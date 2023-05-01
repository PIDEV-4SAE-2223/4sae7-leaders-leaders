package com.example.backend.Repository;

import com.example.backend.Entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FormationRepository extends JpaRepository<Formation,Long> {
    @Query("select formation from Formation formation where formation.name LIKE %:skills%")
    List<Formation> searchCritireaSkills(@Param("skills") String skills);
    @Query("select formation from Formation formation where formation.period = :period")
    List<Formation> searchCritireaPeriod(@Param("period") int period);

   // @Query("select formation from Formation formation where formation.id = :idFormation")
   // Formation findByIdFormation(@Param("idFormation") Long idFormation);
  //  Formation findById(Long id) ;

}