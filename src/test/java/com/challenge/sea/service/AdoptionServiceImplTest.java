package com.challenge.sea.service;

import com.challenge.sea.dto.AdopterDTO;
import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adopter;
import com.challenge.sea.entity.Adoption;
import com.challenge.sea.entity.Animal;
import com.challenge.sea.repository.AdopterRepository;
import com.challenge.sea.repository.AdoptionRepository;
import com.challenge.sea.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AdoptionServiceImplTest {

    @Mock
    private AdoptionRepository adoptionRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private AdopterRepository adopterRepository;
    @InjectMocks
    private AdoptionServiceImpl adoptionService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adoptionService = new AdoptionServiceImpl(adoptionRepository, animalRepository, adopterRepository, modelMapper);
    }

    @Test
    public void testCreateAdoption() {
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setAnimalId(1L);
        adoptionDTO.setAdopterId(1L);
        adoptionDTO.setAdoptionDate(LocalDate.now());
        adoptionDTO.setDevolutionDate(null);

        Animal animal = new Animal();
        animal.setId(1L);
        animal.setStatus("AVAILABLE");

        Adopter adopter = new Adopter();
        adopter.setId(1L);

        Adoption savedAdoption = new Adoption();
        savedAdoption.setId(1L);
        savedAdoption.setAnimal(animal);
        savedAdoption.setAdopter(adopter);
        savedAdoption.setAdoptionDate(adoptionDTO.getAdoptionDate());
        savedAdoption.setDevolutionDate(adoptionDTO.getDevolutionDate());

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(adopterRepository.findById(1L)).thenReturn(Optional.of(adopter));
        when(adoptionRepository.countByAdopterIdAndDevolutionDateIsNull(1L)).thenReturn(2L);
        when(adoptionRepository.save(any(Adoption.class))).thenReturn(savedAdoption);

        Adoption result = adoptionService.createAdoption(adoptionDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(animal, result.getAnimal());
        assertEquals(adopter, result.getAdopter());
        assertEquals(adoptionDTO.getAdoptionDate(), result.getAdoptionDate());
        assertEquals(adoptionDTO.getDevolutionDate(), result.getDevolutionDate());

        verify(adoptionRepository, times(1)).save(any(Adoption.class));
        verify(animalRepository, times(1)).findById(1L);
        verify(adopterRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllAdoptions() {
        Adoption adoption = new Adoption();
        Animal animal = new Animal();
        Adopter adopter = new Adopter();
        adoption.setAnimal(animal);
        adoption.setAdopter(adopter);

        when(adoptionRepository.findAll()).thenReturn(List.of(adoption));
        when(modelMapper.map(adoption, AdoptionDTO.class)).thenReturn(new AdoptionDTO());

        List<AdoptionDTO> result = adoptionService.getAllAdoptions();

        assertNotNull(result);
        verify(adoptionRepository, times(1)).findAll();
    }

    @Test
    public void testGetAdoptionById() {
        Long id = 1L;
        Adoption adoption = new Adoption();
        Animal animal = new Animal();
        Adopter adopter = new Adopter();
        adoption.setAnimal(animal);
        adoption.setAdopter(adopter);

        when(adoptionRepository.findById(id)).thenReturn(Optional.of(adoption));
        when(modelMapper.map(adoption, AdoptionDTO.class)).thenReturn(new AdoptionDTO());

        AdoptionDTO result = adoptionService.getAdoptionById(id);

        assertNotNull(result);
        verify(adoptionRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateAdoption() {
        Long id = 1L;
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setAnimalId(1L);
        adoptionDTO.setAdopterId(1L);
        adoptionDTO.setAdoptionDate(LocalDate.now());

        Adoption existingAdoption = new Adoption();
        Animal animal = new Animal();
        Adopter adopter = new Adopter();
        existingAdoption.setAnimal(animal);
        existingAdoption.setAdopter(adopter);

        when(adoptionRepository.findById(id)).thenReturn(Optional.of(existingAdoption));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(adopterRepository.findById(1L)).thenReturn(Optional.of(adopter));
        when(modelMapper.map(adoptionDTO, Adoption.class)).thenReturn(existingAdoption);
        when(adoptionRepository.save(existingAdoption)).thenReturn(existingAdoption);
        when(modelMapper.map(existingAdoption, AdoptionDTO.class)).thenReturn(adoptionDTO);

        Adoption result = adoptionService.updateAdoption(id, adoptionDTO);

        assertNotNull(result);
        verify(adoptionRepository, times(1)).save(existingAdoption);
    }

    @Test
    public void testGetAdoptionsByAdopterId() {
        Long adopterId = 1L;
        Adoption adoption = new Adoption();
        Animal animal = new Animal();
        Adopter adopter = new Adopter();
        adoption.setAnimal(animal);
        adoption.setAdopter(adopter);

        when(adoptionRepository.findByAdopterId(adopterId)).thenReturn(List.of(adoption));
        when(modelMapper.map(adoption, AdoptionDTO.class)).thenReturn(new AdoptionDTO());

        List<AdoptionDTO> result = adoptionService.getAdoptionsByAdopterId(adopterId);

        assertNotNull(result);
        verify(adoptionRepository, times(1)).findByAdopterId(adopterId);
    }

    @Test
    public void testCancelAdoption() {
        Long id = 1L;
        Adoption adoption = new Adoption();
        Animal animal = new Animal();
        Adopter adopter = new Adopter();
        adoption.setAnimal(animal);
        adoption.setAdopter(adopter);

        when(adoptionRepository.findById(id)).thenReturn(Optional.of(adoption));
        when(animalRepository.save(animal)).thenReturn(animal);
        when(adoptionRepository.save(adoption)).thenReturn(adoption);

        adoptionService.cancelAdoption(id);

        verify(adoptionRepository, times(1)).findById(id);
        verify(animalRepository, times(1)).save(animal);
        verify(adoptionRepository, times(1)).save(adoption);
    }

}
