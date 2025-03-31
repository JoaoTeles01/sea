package com.challenge.sea.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdoptionDTO {
    private Long animalId;
    private Long adopterId;
    private LocalDate adoptionDate;
    private LocalDate devolutionDate;
}
