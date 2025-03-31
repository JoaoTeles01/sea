package com.challenge.sea.utils;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adoption;

public class AdoptionConverter {

    public static AdoptionDTO convertDTO(Adoption adoption){
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setAnimalId(adoption.getId());
        adoptionDTO.setAdopterId(adoption.getAdopter().getId());
        adoptionDTO.setAdoptionDate(adoption.getAdoptionDate());
        adoptionDTO.setDevolutionDate(adoption.getDevolutionDate());
        return adoptionDTO;
    }
}
