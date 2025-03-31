package com.challenge.sea.controller;

import com.challenge.sea.dto.AdopterDTO;
import com.challenge.sea.service.AdopterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adopters")
public class AdopterController {
    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @PostMapping
    public ResponseEntity<AdopterDTO> createAdopter(@RequestBody AdopterDTO adopterDTO) {
        AdopterDTO createdAdopter = adopterService.createAdopter(adopterDTO);
        return new ResponseEntity<>(createdAdopter, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdopterDTO>> getAllAdopters() {
        List<AdopterDTO> adopters = adopterService.getAllAdopters();
        return new ResponseEntity<>(adopters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdopterDTO> getAdopterById(@PathVariable Long id) {
        AdopterDTO adopter = adopterService.getAdopterById(id);
        return new ResponseEntity<>(adopter, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdopterDTO> updateAdopter(@PathVariable Long id, @RequestBody AdopterDTO adopterDTO) {
        AdopterDTO updatedAdopter = adopterService.updateAdopter(id, adopterDTO);
        return new ResponseEntity<>(updatedAdopter, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdopter(@PathVariable Long id) {
        adopterService.deleteAdopter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
