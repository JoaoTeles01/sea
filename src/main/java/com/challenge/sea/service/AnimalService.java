package com.challenge.sea.service;

import com.challenge.sea.dto.AnimalDTO;

import java.util.List;

public interface AnimalService {
    AnimalDTO createAnimal(AnimalDTO animalDTO);
    List<AnimalDTO> getAllAnimals();
    AnimalDTO getAnimalById(Long id);
    AnimalDTO updateAnimal(Long id, AnimalDTO animalDTO);
    void deleteAnimal(Long id);
}
