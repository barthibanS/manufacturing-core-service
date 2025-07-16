package org.sfs.dm.manufacturing_core_service.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupEntityModel {
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50, message = "group key must be between 4 and 50 characters")
    private String name;
}