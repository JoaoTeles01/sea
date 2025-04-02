package com.challenge.sea.service;

import com.challenge.sea.dto.AdopterDTO;
import com.challenge.sea.entity.Adopter;
import com.challenge.sea.repository.AdopterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AdopterServiceImplTest {

    @Mock
    private AdopterRepository adopterRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AdopterServiceImpl adopterService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adopterService = new AdopterServiceImpl(adopterRepository, modelMapper);
    }

    @Test
    public void testCreateAdopter() {
        AdopterDTO adopterDTO = new AdopterDTO();
        adopterDTO.setName("Jonata");
        Adopter adopter = new Adopter();
        Adopter savedAdopter = new Adopter();
        savedAdopter.setId(1L);

        when(modelMapper.map(adopterDTO, Adopter.class)).thenReturn(adopter);
        when(adopterRepository.save(adopter)).thenReturn(savedAdopter);
        when(modelMapper.map(savedAdopter, AdopterDTO.class)).thenReturn(adopterDTO);

        AdopterDTO result = adopterService.createAdopter(adopterDTO);

        assertNotNull(result);
        assertEquals(adopterDTO.getName(), result.getName());
        verify(adopterRepository, times(1)).save(adopter);
    }

    @Test
    public void testGetAllAdopters() {
        List<Adopter> adopters = Arrays.asList(new Adopter(), new Adopter());
        List<AdopterDTO> adopterDTOs = Arrays.asList(new AdopterDTO(), new AdopterDTO());

        when(adopterRepository.findAll()).thenReturn(adopters);
        when(modelMapper.map(adopters.get(0), AdopterDTO.class)).thenReturn(adopterDTOs.get(0));
        when(modelMapper.map(adopters.get(1), AdopterDTO.class)).thenReturn(adopterDTOs.get(1));

        List<AdopterDTO> result = adopterService.getAllAdopters();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(adopterRepository, times(1)).findAll();
    }

    @Test
    public void testGetAdopterById() {
        Long id = 1L;
        Adopter adopter = new Adopter();
        AdopterDTO adopterDTO = new AdopterDTO();

        when(adopterRepository.findById(id)).thenReturn(java.util.Optional.of(adopter));
        when(modelMapper.map(adopter, AdopterDTO.class)).thenReturn(adopterDTO);

        AdopterDTO result = adopterService.getAdopterById(id);

        assertNotNull(result);
        verify(adopterRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateAdopter() {
        Long id = 1L;
        AdopterDTO adopterDTO = new AdopterDTO();
        adopterDTO.setName("Updated Name");
        Adopter existingAdopter = new Adopter();
        existingAdopter.setId(id);
        existingAdopter.setName("Old Name");

        when(adopterRepository.findById(id)).thenReturn(java.util.Optional.of(existingAdopter));
        when(modelMapper.map(adopterDTO, Adopter.class)).thenReturn(existingAdopter);
        when(adopterRepository.save(existingAdopter)).thenReturn(existingAdopter);
        when(modelMapper.map(existingAdopter, AdopterDTO.class)).thenReturn(adopterDTO);

        AdopterDTO result = adopterService.updateAdopter(id, adopterDTO);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(adopterRepository, times(1)).save(existingAdopter);
    }

    @Test
    public void testDeleteAdopter() {
        Long id = 1L;
        Adopter adopter = new Adopter();
        adopter.setId(id);

        when(adopterRepository.findById(id)).thenReturn(java.util.Optional.of(adopter));

        adopterService.deleteAdopter(id);

        verify(adopterRepository, times(1)).delete(adopter);
    }
}
