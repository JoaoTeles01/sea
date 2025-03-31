package com.challenge.sea.config;

import com.challenge.sea.dto.AdoptionDTO;
import com.challenge.sea.entity.Adoption;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<AdoptionDTO, Adoption> typeMap = modelMapper.createTypeMap(AdoptionDTO.class, Adoption.class);
        typeMap.addMappings(mapper ->{
            mapper.map(AdoptionDTO::getAnimalId, Adoption::setId);
        });

        Adoption adoption = modelMapper.map(adoptionDTO, Adoption.class);

        return new ModelMapper();
    }
}
