package com.challenge.sea.service;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adoption;
import com.challenge.sea.repository.AdoptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final ModelMapper adoptionMapper;

    public AdoptionServiceImpl(AdoptionRepository adoptionRepository, ModelMapper adoptionMapper) {
        this.adoptionRepository = adoptionRepository;
        this.adoptionMapper = adoptionMapper;
    }

    @Override
    public AdoptionDTO createAdoption(AdoptionDTO adoptionDTO) {
        Adoption adoption = adoptionMapper.map(adoptionDTO, Adoption.class);
        Adoption savedAdoption = adoptionRepository.save(adoption);
        return adoptionMapper.map(savedAdoption, AdoptionDTO.class);
    }

    @Override
    public List<AdoptionDTO> getAllAdoptions() {
        List<Adoption> adoptions = adoptionRepository.findAll();
        return adoptions.stream()
                .map(adoption -> adoptionMapper.map(adoption, AdoptionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdoptionDTO getAdoptionById(Long id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adoption not found"));
        return adoptionMapper.map(adoption, AdoptionDTO.class);
    }

    @Override
    public AdoptionDTO updateAdoption(Long id, AdoptionDTO adoptionDTO) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adoption not found"));
        adoptionMapper.map(adoptionDTO, adoption);
        Adoption updatedAdoption = adoptionRepository.save(adoption);
        return adoptionMapper.map(updatedAdoption, AdoptionDTO.class);
    }

    @Override
    public void deleteAdoption(Long id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adoption not found"));
        adoptionRepository.delete(adoption);
    }
}
