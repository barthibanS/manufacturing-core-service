package org.sfs.dm.manufacturing_core_service.repository;

import org.sfs.dm.manufacturing_core_service.entity.LoginSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface LoginSessionRepository extends JpaRepository<LoginSession, Long> {
    List<LoginSession> findByDeviceDeviceIdAndLogoutTimeIsNull(Long deviceId);
    Optional<LoginSession> findByPersonIdAndDeviceDeviceIdAndLogoutTimeIsNull(Long personId, Long deviceId);
    List<LoginSession> findByPersonId(Long personId);
    List<LoginSession> findByDeviceDeviceIdInAndPersonIdIn(List<Long> deviceIds, List<Long> personIds);
} 