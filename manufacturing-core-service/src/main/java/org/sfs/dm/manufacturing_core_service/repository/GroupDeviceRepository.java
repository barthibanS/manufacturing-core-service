package org.sfs.dm.manufacturing_core_service.repository;

import org.sfs.dm.manufacturing_core_service.entity.GroupDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupDeviceRepository  extends JpaRepository<GroupDevice, Long> {
    Optional<GroupDevice> findByGroupIdAndDeviceDeviceId(Long groupId, Long deviceId);

    List<GroupDevice> findByGroupId(Long groupId);
}
