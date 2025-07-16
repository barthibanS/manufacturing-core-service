package org.sfs.dm.manufacturing_core_service.repository;

import org.sfs.dm.manufacturing_core_service.entity.DevicePlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevicePlantRepository extends JpaRepository<DevicePlant, Long> {
    Optional<DevicePlant> findByDeviceDeviceId(Long deviceId);
}
