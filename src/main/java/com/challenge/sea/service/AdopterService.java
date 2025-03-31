package com.challenge.sea.service;

import com.challenge.sea.dto.AdopterDTO;

import java.util.List;

public interface AdopterService {
    AdopterDTO createAdopter(AdopterDTO adopterDTO);
    List<AdopterDTO> getAllAdopters();
    AdopterDTO getAdopterById(Long id);
    AdopterDTO updateAdopter(Long id, AdopterDTO adopterDTO);
    void deleteAdopter(Long id);
}
