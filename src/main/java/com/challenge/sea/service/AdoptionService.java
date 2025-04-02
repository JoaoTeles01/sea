package com.challenge.sea.service;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adoption;

import java.util.List;

public interface AdoptionService {
    Adoption createAdoption(AdoptionDTO adoptionDTO);
    List<AdoptionDTO> getAllAdoptions();
    AdoptionDTO getAdoptionById(Long id);
    Adoption updateAdoption(Long id, AdoptionDTO adoptionDTO);
    List<AdoptionDTO> getAdoptionsByAdopterId(Long adopterId);
    void cancelAdoption(Long id);
}
