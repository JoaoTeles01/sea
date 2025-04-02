package com.challenge.sea.controller;

import com.challenge.sea.dto.AdopterDTO;
import com.challenge.sea.service.AdopterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdopterController.class)
@AutoConfigureMockMvc
public class AdopterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdopterService adopterService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAdopter() throws Exception {
        AdopterDTO adopterDTO = new AdopterDTO();
        adopterDTO.setName("Claudio");
        when(adopterService.createAdopter(any(AdopterDTO.class))).thenReturn(adopterDTO);

        mockMvc.perform(post("/api/adopters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adopterDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllAdopters() throws Exception {
        List<AdopterDTO> adopters = Arrays.asList(new AdopterDTO(), new AdopterDTO());
        when(adopterService.getAllAdopters()).thenReturn(adopters);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adopters"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetAdopterById() throws Exception {
        AdopterDTO adopterDTO = new AdopterDTO();
        when(adopterService.getAdopterById(1L)).thenReturn(adopterDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/adopters/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateAdopter() throws Exception {
        AdopterDTO adopterDTO = new AdopterDTO();
        when(adopterService.updateAdopter(1L, adopterDTO)).thenReturn(adopterDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/adopters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adopterDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAdopter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/adopters/1"))
                .andExpect(status().isNoContent());
    }


}
