package com.challenge.sea.repository;

import com.challenge.sea.entity.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
}
