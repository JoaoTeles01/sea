package com.challenge.sea.service;

import com.challenge.sea.dto.AnimalDTO;
import com.challenge.sea.entity.Animal;
import com.challenge.sea.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AnimalServiceImpl animalService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        animalService = new AnimalServiceImpl(animalRepository, modelMapper);
    }

    @Test
    public void testCreateAnimal() {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("Dolphin");
        Animal animal = new Animal();
        Animal savedAnimal = new Animal();
        savedAnimal.setId(1L);

        when(modelMapper.map(animalDTO, Animal.class)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(savedAnimal);
        when(modelMapper.map(savedAnimal, AnimalDTO.class)).thenReturn(animalDTO);

        AnimalDTO result = animalService.createAnimal(animalDTO);

        assertNotNull(result);
        assertEquals("Claudio", result.getName());
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    public void  testGetAllAnimals() {
        List<Animal> animals = Arrays.asList(new Animal(), new Animal());
        List<AnimalDTO> animalDTOs = Arrays.asList(new AnimalDTO(), new AnimalDTO());

        when(animalRepository.findAll()).thenReturn(animals);
        when(modelMapper.map(animals.get(0), AnimalDTO.class)).thenReturn(animalDTOs.get(0));
        when(modelMapper.map(animals.get(1), AnimalDTO.class)).thenReturn(animalDTOs.get(1));
        List<AnimalDTO> result = animalService.getAllAnimals();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(animalRepository, times(1)).findAll();
    }

    @Test
    public void testGetAnimalById() {
        Animal animal = new Animal();
        animal.setId(1L);
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("Rex");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(modelMapper.map(animal, AnimalDTO.class)).thenReturn(animalDTO);

        AnimalDTO result = animalService.getAnimalById(1L);

        assertNotNull(result);
        assertEquals("Rex", result.getName());
        verify(animalRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateAnimal() {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("Leo");
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Leo");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(modelMapper.map(animalDTO, Animal.class)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(modelMapper.map(animal, AnimalDTO.class)).thenReturn(animalDTO);

        AnimalDTO result = animalService.updateAnimal(1L, animalDTO);

        assertNotNull(result);
        assertEquals("Leo", result.getName());
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    public void testDeleteAnimal() {
        Animal animal = new Animal();
        animal.setId(1L);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        doNothing().when(animalRepository).delete(animal);

        animalService.deleteAnimal(1L);

        verify(animalRepository, times(1)).delete(animal);
    }
}
