package com.challenge.sea.controller;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.service.AdoptionService;
import com.challenge.sea.utils.AdoptionConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdoptionConverter.class)
@AutoConfigureMockMvc
public class AdoptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdoptionService adoptionService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAdoption() throws Exception {
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setAnimalId(1L);
        adoptionDTO.setAdopterId(1L);

        mockMvc.perform(post("/api/adoption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adoptionDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllAdoptions() throws Exception {
        List<AdoptionDTO> adoptions = Arrays.asList(new AdoptionDTO(), new AdoptionDTO());
        when(adoptionService.getAllAdoptions()).thenReturn(adoptions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adoption"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetAdoptionById() throws Exception {
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        when(adoptionService.getAdoptionById(1L)).thenReturn(adoptionDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adoption/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateAdoption() throws Exception {
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setAnimalId(2L);

        mockMvc.perform(put("/api/adoption/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adoptionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId").value(2));
    }
}
