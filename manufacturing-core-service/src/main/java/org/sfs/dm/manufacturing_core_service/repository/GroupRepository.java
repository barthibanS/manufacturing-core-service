package org.sfs.dm.manufacturing_core_service.repository;

import org.sfs.dm.manufacturing_core_service.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
