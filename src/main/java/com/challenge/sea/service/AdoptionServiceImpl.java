package com.challenge.sea.service;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adopter;
import com.challenge.sea.entity.Adoption;
import com.challenge.sea.entity.Animal;
import com.challenge.sea.exception.AdoptionException;
import com.challenge.sea.exception.ResourceNotFoundException;
import com.challenge.sea.repository.AdopterRepository;
import com.challenge.sea.repository.AdoptionRepository;
import com.challenge.sea.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
        if (animal.getStatus().equals("ADOPTED")) {
            throw new AdoptionException("Animal is already adopted.");
        }
        long adoptedCount = adoptionRepository.countByAdopterIdAndDevolutionDateIsNull(animal.getId());
        if(adoptedCount >= 3) {
            throw new AdoptionException("Adopter cannot adopt more than 3 animals.");
        }
        animal.setStatus("ADOPTED");
        adoption.setAnimal(animal);
        adoption.setAdopter(adopter);
        adoption.setAdoptionDate(adoptionDTO.getAdoptionDate());
        adoption.setDevolutionDate(adoptionDTO.getDevolutionDate());
        Adoption savedAdoption = adoptionRepository.save(adoption);
        return adoptionMapper.map(savedAdoption, Adoption.class);
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
                .orElseThrow(() -> new ResourceNotFoundException("Adoption not found with id: " + id));
        return adoptionMapper.map(adoption, AdoptionDTO.class);
    }

    @Override
    public Adoption updateAdoption(Long id, AdoptionDTO adoptionDTO) {
        Optional<Adoption> optionalAdoption = adoptionRepository.findById(id);
        if (optionalAdoption.isPresent()) {
            Adoption adoption = optionalAdoption.get();
            Animal animal = animalRepository.findById(adoptionDTO.getAnimalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Adoption not found with id: " + id));

            Adopter adopter = adopterRepository.findById(adoptionDTO.getAdopterId())
                    .orElseThrow(() -> new ResourceNotFoundException("Adoption not found with id: " + id));
            adoption.setAnimal(animal);
            adoption.setAdopter(adopter);
            adoption.setAdoptionDate(adoptionDTO.getAdoptionDate());
            adoption.setDevolutionDate(adoptionDTO.getDevolutionDate());
            return adoptionRepository.save(adoption);
        } else {
            throw new RuntimeException("Adoption not found");
        }
    }

    @Override
    public List<AdoptionDTO> getAdoptionsByAdopterId(Long adopterId) {
        List<Adoption> adoptions = adoptionRepository.findByAdopterId(adopterId);
        return adoptions.stream()
                .map(adoption -> adoptionMapper.map(adoption, AdoptionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelAdoption(Long id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adoption not found"));
        Animal animal = adoption.getAnimal();
        animal.setStatus("AVAILABLE");
        animalRepository.save(animal);
        adoption.setDevolutionDate(LocalDate.now());
        adoptionRepository.save(adoption);
    }
}
