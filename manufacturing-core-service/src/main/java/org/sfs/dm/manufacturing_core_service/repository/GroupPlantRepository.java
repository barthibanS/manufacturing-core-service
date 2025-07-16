package org.sfs.dm.manufacturing_core_service.repository;

import org.sfs.dm.manufacturing_core_service.entity.GroupPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupPlantRepository  extends JpaRepository<GroupPlant, Long> {
    Optional<GroupPlant> findByGroupId(Long groupId);
}
