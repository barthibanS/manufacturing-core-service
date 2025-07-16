package org.sfs.dm.manufacturing_core_service.repository;

import org.sfs.dm.manufacturing_core_service.entity.PersonGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonGroupRepository  extends JpaRepository<PersonGroup, Long> {
    List<PersonGroup> findByPersonId(Long personId);

    List<PersonGroup> findByGroupId(Long groupId);
}
