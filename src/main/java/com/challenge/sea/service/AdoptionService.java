package com.challenge.sea.service;

import com.challenge.sea.dto.AdoptionDTO;

import java.util.List;

public interface AdoptionService {
    AdoptionDTO createAdoption(AdoptionDTO adoptionDTO);
    List<AdoptionDTO> getAllAdoptions();
    AdoptionDTO getAdoptionById(Long id);
    AdoptionDTO updateAdoption(Long id, AdoptionDTO adoptionDTO);
    void deleteAdoption(Long id);
}
