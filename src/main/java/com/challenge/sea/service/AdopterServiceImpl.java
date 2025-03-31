package com.challenge.sea.service;

import com.challenge.sea.dto.AdopterDTO;
import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adopter;
import com.challenge.sea.repository.AdopterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdopterServiceImpl implements AdopterService {

    private final AdopterRepository adopterRepository;
    private final ModelMapper adopterMapper;

    public AdopterServiceImpl(AdopterRepository adopterRepository, ModelMapper adopterMapper) {
        this.adopterRepository = adopterRepository;
        this.adopterMapper = adopterMapper;
    }

    @Override
    public AdopterDTO createAdopter(AdopterDTO adopterDTO) {
        Adopter adopter = adopterMapper.map(adopterDTO, Adopter.class);
        Adopter savedAdopter = adopterRepository.save(adopter);
        return adopterMapper.map(savedAdopter, AdopterDTO.class);
    }

    @Override
    public List<AdopterDTO> getAllAdopters(){
        var adopters = adopterRepository.findAll();
        return adopters.stream()
                .map(adopter -> adopterMapper.map(adopter, AdopterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdopterDTO getAdopterById(Long id) {
        var adopter = adopterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopter not found"));
        return adopterMapper.map(adopter, AdopterDTO.class);
    }

    @Override
    public AdopterDTO updateAdopter(Long id, AdopterDTO adopterDTO) {
        Adopter adopter = adopterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopter not found"));
        adopterMapper.map(adopterDTO, adopter);
        Adopter updatedAdopter = adopterRepository.save(adopter);
        return adopterMapper.map(updatedAdopter, AdopterDTO.class);
    }

    @Override
    public void deleteAdopter(Long id) {
        var adopter = adopterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopter not found"));
        adopterRepository.delete(adopter);
    }
}
