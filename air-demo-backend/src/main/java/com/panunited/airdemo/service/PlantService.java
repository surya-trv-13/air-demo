package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.PlantResponse;
import com.panunited.airdemo.models.Plant;
import com.panunited.airdemo.repositories.PlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PlantService {
    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public Optional<Plant> findById(long plantId) {
        return plantRepository.findById(plantId);
    }

    public List<PlantResponse> getActivePlants() {
        return plantRepository.findActivePlants();
    }
}
