package org.sfs.dm.manufacturing_core_service.service;

import org.sfs.dm.manufacturing_core_service.entity.*;
import org.sfs.dm.manufacturing_core_service.exception.AssignmentConflictException;
import org.sfs.dm.manufacturing_core_service.exception.EntityNotFoundException;
import org.sfs.dm.manufacturing_core_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeviceLoginService {
    @Autowired private DeviceRepository deviceRepo;
    @Autowired private PersonRepository personRepo;
    @Autowired private GroupDeviceRepository groupDeviceRepo;
    @Autowired private LoginSessionRepository loginSessionRepo;
    @Autowired private PersonGroupRepository personGroupRepo;

    public void login(Long personId, Long deviceId) {
        synchronized (this) {
            Device device = deviceRepo.findById(deviceId)
                    .orElseThrow(() -> new EntityNotFoundException("Device not found: " + deviceId));
            Person person = personRepo.findById(personId)
                    .orElseThrow(() -> new EntityNotFoundException("Person not found: " + personId));

            // Check if device is associated with any group of the person
            List<PersonGroup> personGroups = personGroupRepo.findByPersonId(personId);
            boolean allowed = personGroups.stream().anyMatch(pg ->
                    groupDeviceRepo.findByGroupIdAndDeviceDeviceId(pg.getGroup().getId(), deviceId).isPresent()
            );
            if (!allowed) {
                throw new AssignmentConflictException("Device is not associated with any group of the employee");
            }

            // Check if device is at max capacity
            int activeSessions = loginSessionRepo.findByDeviceDeviceIdAndLogoutTimeIsNull(deviceId).size();
            if (activeSessions >= device.getRequiredOperators()) {
                throw new AssignmentConflictException("Device is at maximum operator capacity");
            }

            // Prevent duplicate login
            if (loginSessionRepo.findByPersonIdAndDeviceDeviceIdAndLogoutTimeIsNull(personId, deviceId).isPresent()) {
                throw new AssignmentConflictException("Employee is already logged in to this device");
            }

            // Create login session
            LoginSession session = new LoginSession();
            session.setPerson(person);
            session.setDevice(device);
            session.setLoginTime(LocalDateTime.now());
            loginSessionRepo.save(session);
        }
    }

    public void logout(Long personId, Long deviceId) {
        LoginSession session = loginSessionRepo.findByPersonIdAndDeviceDeviceIdAndLogoutTimeIsNull(personId, deviceId)
                .orElseThrow(() -> new AssignmentConflictException("No active login session found for employee on this device"));
        session.setLogoutTime(LocalDateTime.now());
        loginSessionRepo.save(session);
    }

    public List<LoginSession> getTimeLogs(Long personId) {
        return loginSessionRepo.findByPersonId(personId);
    }
} 