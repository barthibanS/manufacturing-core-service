package org.sfs.dm.manufacturing_core_service.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeviceEvent {
    private long timestamp;
    private double partsPerMinute;
    private String status;
    private String deviceId;
    private String order;

}