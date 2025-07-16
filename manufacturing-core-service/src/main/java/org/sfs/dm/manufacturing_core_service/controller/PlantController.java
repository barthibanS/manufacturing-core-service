package org.sfs.dm.manufacturing_core_service.controller;

import org.sfs.dm.manufacturing_core_service.config.PlantConfig;
import org.sfs.dm.manufacturing_core_service.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController {

    private final PlantConfig plantConfig;

    @Autowired
    public PlantController(PlantConfig plantConfig) {
        this.plantConfig = plantConfig;
    }

    @GetMapping
    public List<Plant> getPlants() {
        return plantConfig.getPlants();
    }
}
