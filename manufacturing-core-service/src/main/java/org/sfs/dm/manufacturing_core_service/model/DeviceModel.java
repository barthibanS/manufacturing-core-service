package org.sfs.dm.manufacturing_core_service.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class DeviceModel {
    private Long deviceId;

    @NotBlank
    @Size(min = 4, max = 50, message = "device key must be between 4 and 50 characters")
    private String type;

    @NotNull
    @Min(value = 1, message = "mini required operators is 1")
    private int requiredOperators;
}