package org.sfs.dm.manufacturing_core_service.config;

import lombok.Getter;
import lombok.Setter;
import org.sfs.dm.manufacturing_core_service.model.Plant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "")
public class PlantConfig {
    private final List<Plant> plants;

    public PlantConfig(List<Plant> plants) {
        this.plants = plants;
    }
}