package com.challenge.sea.controller;

import com.challenge.sea.dto.AnimalDTO;
import com.challenge.sea.entity.Animal;
import com.challenge.sea.repository.AnimalRepository;
import com.challenge.sea.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalController.class)
@AutoConfigureMockMvc
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AnimalService animalService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AnimalRepository animalRepository;

    @Test
    public void testCreateAnimal() throws Exception {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("Claudio");
        when(animalService.createAnimal(any(AnimalDTO.class))).thenReturn(animalDTO);

        mockMvc.perform(post("/api/animals").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(animalDTO))).andExpect(status().isCreated());

    }

    @Test
    public void testGetAllAnimals() throws  Exception{
        List<AnimalDTO> animals = Arrays.asList(new AnimalDTO(), new AnimalDTO());
        when(animalService.getAllAnimals()).thenReturn(animals);

        mockMvc.perform(get("/api/animals")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetAnimalById() throws Exception {
        AnimalDTO animalDTO = new AnimalDTO();
        when(animalService.getAnimalById(1L)).thenReturn(animalDTO);

        mockMvc.perform(get("/api/animals/1"))
                .andExpect(status().isOk());

        Animal animal = animalRepository.findById(1L).orElse(null);
        assertNotNull(animal);
        assertEquals(1L, animal.getId());
    }

    @Test
    public void testUpdateAnimal() throws Exception {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("Claudio");
        when(animalService.updateAnimal(1L, animalDTO)).thenReturn(animalDTO);

        mockMvc.perform(post("/api/animals/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(animalDTO)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Claudio"));
    }

    @Test
    public void testDeleteAnimal() throws Exception {
        mockMvc.perform(post("/api/animals/1/delete"))
                .andExpect(status().isOk());
    }
}
