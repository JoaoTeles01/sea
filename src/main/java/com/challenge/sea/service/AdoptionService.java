package com.challenge.sea.service;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adoption;

import java.util.List;

public interface AdoptionService {
    Adoption createAdoption(AdoptionDTO adoptionDTO);
    List<AdoptionDTO> getAllAdoptions();
    AdoptionDTO getAdoptionById(Long id);
    AdoptionDTO updateAdoption(Long id, AdoptionDTO adoptionDTO);
    void deleteAdoption(Long id);
}
