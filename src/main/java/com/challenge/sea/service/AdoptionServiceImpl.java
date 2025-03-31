package com.challenge.sea.service;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adopter;
import com.challenge.sea.entity.Adoption;
import com.challenge.sea.entity.Animal;
import com.challenge.sea.repository.AdopterRepository;
import com.challenge.sea.repository.AdoptionRepository;
import com.challenge.sea.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final AnimalRepository animalRepository;
    private final AdopterRepository adopterRepository;
    private final ModelMapper adoptionMapper;

    public AdoptionServiceImpl(AdoptionRepository adoptionRepository, AnimalRepository animalRepository, AdopterRepository adopterRepository, ModelMapper adoptionMapper) {
        this.adoptionRepository = adoptionRepository;
        this.animalRepository = animalRepository;
        this.adopterRepository = adopterRepository;
        this.adoptionMapper = adoptionMapper;
    }

    @Override
    public Adoption createAdoption(AdoptionDTO adoptionDTO) {
        Adoption adoption = new Adoption();
        Animal animal = animalRepository.findById(adoptionDTO.getAnimalId()).orElseThrow(() -> new RuntimeException("Animal not found"));
        Adopter adopter = adopterRepository.findById(adoptionDTO.getAdopterId()).orElseThrow(() -> new RuntimeException("Adopter not found"));
        adoption.setAnimal(animal);
        adoption.setAdopter(adopter);
        adoption.setAdoptionDate(adoptionDTO.getAdoptionDate());
        adoption.setDevolutionDate(adoptionDTO.getDevolutionDate());
        return adoptionRepository.save(adoption);
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
