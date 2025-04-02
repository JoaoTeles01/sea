package com.challenge.sea.service;

import com.challenge.sea.dto.AnimalDTO;
import com.challenge.sea.entity.Animal;
import com.challenge.sea.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper animalMapper;

    public AnimalServiceImpl(AnimalRepository animalRepository, ModelMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    @Override
    public AnimalDTO createAnimal(AnimalDTO animalDTO) {
        Animal animal = animalMapper.map(animalDTO, Animal.class);
        animal.setStatus("AVAILABLE");
        Animal savedAnimal = animalRepository.save(animal);
        return animalMapper.map(savedAnimal, AnimalDTO.class);
    }

    @Override
    public List<AnimalDTO> getAllAnimals() {
        List<Animal> animals = animalRepository.findAll();
        return animals.stream()
                .map(animal -> animalMapper.map(animal, AnimalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnimalDTO getAnimalById(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        return animalMapper.map(animal, AnimalDTO.class);
    }

    @Override
    public AnimalDTO updateAnimal(Long id, AnimalDTO animalDTO) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        animalMapper.map(animalDTO, animal);
        Animal updatedAnimal = animalRepository.save(animal);
        return animalMapper.map(updatedAnimal, AnimalDTO.class);
    }

    @Override
    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        animalRepository.delete(animal);
    }

}
