package com.challenge.sea.controller;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.service.AdoptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adoption")
public class AdoptionController {

    private final AdoptionService adoptionService;

    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @PostMapping
    public ResponseEntity<AdoptionDTO> createAdoption(@RequestBody AdoptionDTO adoptionDTO) {
        AdoptionDTO createdAdoption = adoptionService.createAdoption(adoptionDTO);
        return new ResponseEntity<>(createdAdoption, HttpStatus.CREATED);
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
        AdoptionDTO updatedAdoption = adoptionService.updateAdoption(id, adoptionDTO);
        return new ResponseEntity<>(updatedAdoption, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdoption(@PathVariable Long id) {
        adoptionService.deleteAdoption(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
