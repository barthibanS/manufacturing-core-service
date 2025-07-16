package org.sfs.dm.manufacturing_core_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPerformanceDTO {
    private Long groupId;
    private List<DeviceStats> devices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceStats {
        private Long deviceId;
        private long totalOperationTimeSeconds;
        private double utilizationRatePercent;
    }
} 