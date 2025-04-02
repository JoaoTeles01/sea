package com.challenge.sea.repository;

import com.challenge.sea.entity.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    long countByAdopterIdAndDevolutionDateIsNull(Long adopterId);
    List<Adoption> findByAdopterId(Long adopterId);
}
