package org.sfs.dm.manufacturing_core_service.model;

import lombok.Data;

@Data
public class DeviceModel {
    private Long deviceId;
    private String type;
    private int requiredOperators;
}