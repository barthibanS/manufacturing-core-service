package org.sfs.dm.manufacturing_core_service.service;

import org.sfs.dm.manufacturing_core_service.config.PlantConfig;
import org.sfs.dm.manufacturing_core_service.entity.*;
import org.sfs.dm.manufacturing_core_service.model.Plant;
import org.sfs.dm.manufacturing_core_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sfs.dm.manufacturing_core_service.exception.EntityNotFoundException;
import org.sfs.dm.manufacturing_core_service.exception.AssignmentConflictException;

import java.util.Optional;

@Service
public class AssignmentsService {
    private static final Logger log = LoggerFactory.getLogger(AssignmentsService.class);

    @Autowired private DeviceRepository deviceRepo;
    @Autowired private PersonRepository personRepo;
    @Autowired private GroupRepository groupRepo;
    @Autowired private PlantConfig plantRepo;
    @Autowired private PersonGroupRepository personGroupRepo;
    @Autowired private GroupPlantRepository groupPlantRepo;
    @Autowired private DevicePlantRepository devicePlantRepo;
    @Autowired private GroupDeviceRepository groupDeviceRepo;

    public void assignPersonToGroup(Long personId, Long groupId) {
        log.info("Assigning person {} to group {}", personId, groupId);
        Person person = fetchPerson(personId);
        GroupEntity group = fetchGroup(groupId);
        PersonGroup assignment = new PersonGroup();
        assignment.setPerson(person);
        assignment.setGroup(group);
        personGroupRepo.save(assignment);
        log.info("Assigned person {} to group {} successfully", personId, groupId);
    }

    public void assignGroupToPlant(Long groupId, String plantKey) {
        log.info("Assigning group {} to plant {}", groupId, plantKey);
        GroupEntity group = fetchGroup(groupId);
        Plant plant = fetchPlant(plantKey);
        Optional<GroupPlant> existing = groupPlantRepo.findByGroupId(groupId);
        existing.ifPresent(groupPlant -> throwAssignmentConflict("Group {} is already assigned to a plant ({}), cannot reassign", groupId, groupPlant.getPlantKey()));
        GroupPlant link = new GroupPlant();
        link.setGroup(group);
        link.setPlantKey(plant.getPlantKey());
        groupPlantRepo.save(link);
        log.info("Assigned group {} to plant {} successfully", groupId, plantKey);
    }

    public void assignDeviceToPlant(Long deviceId, String plantKey) {
        log.info("Assigning device {} to plant {}", deviceId, plantKey);
        Device device = fetchDevice(deviceId);
        Plant plant = fetchPlant(plantKey);
        Optional<DevicePlant> existing = devicePlantRepo.findByDeviceDeviceId(deviceId);
        existing.ifPresent(devicePlant -> throwAssignmentConflict("Device {} is already assigned to a plant ({}), cannot reassign", deviceId, devicePlant.getPlantKey()));
        DevicePlant link = new DevicePlant();
        link.setDevice(device);
        link.setPlantKey(plant.getPlantKey());
        devicePlantRepo.save(link);
        log.info("Assigned device {} to plant {} successfully", deviceId, plantKey);
    }

    public void assignGroupToDevice(Long groupId, Long deviceId) {
        log.info("Assigning device {} to group {}", deviceId, groupId);
        Device device = fetchDevice(deviceId);
        GroupEntity group = fetchGroup(groupId);
        DevicePlant devicePlant = devicePlantRepo.findByDeviceDeviceId(deviceId)
                .orElseThrow(() -> assignmentConflict("Device {} not assigned to any plant", deviceId));
        GroupPlant groupPlant = groupPlantRepo.findByGroupId(groupId)
                .orElseThrow(() -> assignmentConflict("Group {} not assigned to any plant", groupId));
        if (!devicePlant.getPlantKey().equals(groupPlant.getPlantKey())) {
            throwAssignmentConflict("Device {} and Group {} do not belong to the same plant", deviceId, groupId);
        }
        if (groupDeviceRepo.findByGroupIdAndDeviceDeviceId(groupId, deviceId).isPresent()) {
            throwAssignmentConflict("Device {} is already assigned to group {}", deviceId, groupId);
        }
        GroupDevice assignment = new GroupDevice();
        assignment.setGroup(group);
        assignment.setDevice(device);
        groupDeviceRepo.save(assignment);
        log.info("Assigned device {} to group {} successfully", deviceId, groupId);
    }

    // --- Reusable private helpers ---
    private Person fetchPerson(Long personId) {
        return personRepo.findById(personId)
                .orElseThrow(() -> entityNotFound("Person {} not found", personId));
    }

    private GroupEntity fetchGroup(Long groupId) {
        return groupRepo.findById(groupId)
                .orElseThrow(() -> entityNotFound("Group {} not found", groupId));
    }

    private Device fetchDevice(Long deviceId) {
        return deviceRepo.findById(deviceId)
                .orElseThrow(() -> entityNotFound("Device {} not found", deviceId));
    }

    private Plant fetchPlant(String plantKey) {
        return plantRepo.getPlants().stream()
                .filter(p -> p.getPlantKey().equals(plantKey))
                .findFirst()
                .orElseThrow(() -> entityNotFound("Plant {} not found", plantKey));
    }

    // --- Logging and Exception helpers ---
    private void logError(String msg, Object... args) {
        log.error(msg, args);
    }
    private void throwAssignmentConflict(String msg, Object... args) {
        logError(msg, args);
        throw new AssignmentConflictException(format(msg, args));
    }
    private AssignmentConflictException assignmentConflict(String msg, Object... args) {
        logError(msg, args);
        return new AssignmentConflictException(format(msg, args));
    }
    private EntityNotFoundException entityNotFound(String msg, Object... args) {
        logError(msg, args);
        return new EntityNotFoundException(format(msg, args));
    }
    private String format(String msg, Object... args) {
        return args == null || args.length == 0 ? msg : String.format(replaceBraces(msg), args);
    }
    private String replaceBraces(String msg) {
        return msg.replaceAll("\\{\\}", "%s");
    }
}