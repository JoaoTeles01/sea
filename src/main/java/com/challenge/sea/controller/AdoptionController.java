package com.challenge.sea.controller;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adoption;
import com.challenge.sea.service.AdoptionService;
import com.challenge.sea.utils.AdoptionConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoption")
public class AdoptionController {

    private final AdoptionService adoptionService;

    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @PostMapping
    public ResponseEntity<AdoptionDTO> createAdoption(@RequestBody AdoptionDTO adoptionDTO) {
        Adoption createdAdoption = adoptionService.createAdoption(adoptionDTO);
        AdoptionDTO dto = AdoptionConverter.convertDTO(createdAdoption);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdoptionDTO>> getAllAdoptions() {
        List<AdoptionDTO> adoptions = adoptionService.getAllAdoptions();
        return new ResponseEntity<>(adoptions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionDTO> getAdoptionById(@PathVariable Long id) {
        AdoptionDTO adoption = adoptionService.getAdoptionById(id);
        return new ResponseEntity<>(adoption, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdoptionDTO> updateAdoption(@PathVariable Long id, @RequestBody AdoptionDTO adoptionDTO) {
        Adoption updatedAdoption = adoptionService.updateAdoption(id, adoptionDTO);
        AdoptionDTO dto = AdoptionConverter.convertDTO(updatedAdoption);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/adopter/{adopterId}")
    public ResponseEntity<List<AdoptionDTO>> getAdoptionsByAdopterId(@PathVariable Long adopterId) {
        List<AdoptionDTO> adoptions = adoptionService.getAdoptionsByAdopterId(adopterId);
        return new ResponseEntity<>(adoptions, HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAdoption(@PathVariable Long id) {
        adoptionService.cancelAdoption(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
