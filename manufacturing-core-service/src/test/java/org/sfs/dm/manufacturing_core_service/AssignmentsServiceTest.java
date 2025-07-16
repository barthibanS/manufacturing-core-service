package org.sfs.dm.manufacturing_core_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sfs.dm.manufacturing_core_service.config.PlantConfig;
import org.sfs.dm.manufacturing_core_service.entity.*;
import org.sfs.dm.manufacturing_core_service.exception.AssignmentConflictException;
import org.sfs.dm.manufacturing_core_service.exception.EntityNotFoundException;
import org.sfs.dm.manufacturing_core_service.model.Plant;
import org.sfs.dm.manufacturing_core_service.repository.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentsServiceTest {
    @Mock private DeviceRepository deviceRepo;
    @Mock private PersonRepository personRepo;
    @Mock private GroupRepository groupRepo;
    @Mock private PlantConfig plantRepo;
    @Mock private PersonGroupRepository personGroupRepo;
    @Mock private GroupPlantRepository groupPlantRepo;
    @Mock private DevicePlantRepository devicePlantRepo;
    @Mock private GroupDeviceRepository groupDeviceRepo;

    @InjectMocks private org.sfs.dm.manufacturing_core_service.service.AssignmentsService service;

    private Person person;
    private GroupEntity group;
    private Device device;
    private Plant plant;
    private GroupPlant groupPlant;
    private DevicePlant devicePlant;
    private GroupDevice groupDevice;

    @BeforeEach
    void setup() {
        person = new Person();
        person.setId(1L);
        group = new GroupEntity();
        group.setId(2L);
        device = new Device();
        device.setDeviceId(3L);
        plant = new Plant();
        groupPlant = new GroupPlant();
        groupPlant.setId(4L);
        groupPlant.setGroup(group);
        groupPlant.setPlantKey("PL001");
        devicePlant = new DevicePlant();
        devicePlant.setId(5L);
        devicePlant.setDevice(device);
        devicePlant.setPlantKey("PL001");
        groupDevice = new GroupDevice();
        groupDevice.setGroup(group);
        groupDevice.setDevice(device);
    }

    // assignPersonToGroup
    @Test
    void assignPersonToGroup_success() {
        when(personRepo.findById(1L)).thenReturn(Optional.of(person));
        when(groupRepo.findById(2L)).thenReturn(Optional.of(group));
        service.assignPersonToGroup(1L, 2L);
        verify(personGroupRepo).save(any(PersonGroup.class));
    }
    @Test
    void assignPersonToGroup_personNotFound() {
        when(personRepo.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignPersonToGroup(1L, 2L));
        assertEquals("Person 1 not found", ex.getMessage());
    }
    @Test
    void assignPersonToGroup_groupNotFound() {
        when(personRepo.findById(anyLong())).thenReturn(Optional.of(person));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignPersonToGroup(1L, 2L));
        assertEquals("Group 2 not found", ex.getMessage());
    }

    // assignGroupToPlant
    @Test
    void assignGroupToPlant_success() {
        when(groupRepo.findById(2L)).thenReturn(Optional.of(group));
        when(plantRepo.getPlants()).thenReturn(Arrays.asList(new Plant("PL001","Moranville")));
        when(groupPlantRepo.findByGroupId(2L)).thenReturn(Optional.empty());
        service.assignGroupToPlant(2L, "PL001");
        verify(groupPlantRepo).save(any(GroupPlant.class));
    }
    @Test
    void assignGroupToPlant_groupNotFound() {
        when(groupRepo.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignGroupToPlant(2L, "PL001"));
        assertEquals("Group 2 not found", ex.getMessage());
    }
    @Test
    void assignGroupToPlant_plantNotFound() {
        when(groupRepo.findById(2L)).thenReturn(Optional.of(group));
        when(plantRepo.getPlants()).thenReturn(Collections.emptyList());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignGroupToPlant(2L, "PL001"));
        assertEquals("Plant PL001 not found", ex.getMessage());
    }
    @Test
    void assignGroupToPlant_conflict() {
        when(groupRepo.findById(2L)).thenReturn(Optional.of(group));
        when(plantRepo.getPlants()).thenReturn(Arrays.asList(new Plant("PL001","Moranville")));
        when(groupPlantRepo.findByGroupId(2L)).thenReturn(Optional.of(groupPlant));
        Exception ex = assertThrows(AssignmentConflictException.class, () -> service.assignGroupToPlant(2L, "PL001"));
        assertEquals("Group 2 is already assigned to a plant (PL001), cannot reassign", ex.getMessage());
    }

    // assignDeviceToPlant
    @Test
    void assignDeviceToPlant_success() {
        when(deviceRepo.findById(3L)).thenReturn(Optional.of(device));
        when(plantRepo.getPlants()).thenReturn(Arrays.asList(new Plant("PL001","Moranville")));
        when(devicePlantRepo.findByDeviceDeviceId(3L)).thenReturn(Optional.empty());
        service.assignDeviceToPlant(3L, "PL001");
        verify(devicePlantRepo).save(any(DevicePlant.class));
    }
    @Test
    void assignDeviceToPlant_deviceNotFound() {
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignDeviceToPlant(3L, "PL001"));
        assertEquals("Device 3 not found", ex.getMessage());
    }
    @Test
    void assignDeviceToPlant_plantNotFound() {
        when(deviceRepo.findById(3L)).thenReturn(Optional.of(device));
        when(plantRepo.getPlants()).thenReturn(Collections.emptyList());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignDeviceToPlant(3L, "PL001"));
        assertEquals("Plant PL001 not found", ex.getMessage());
    }
    @Test
    void assignDeviceToPlant_conflict() {
        when(deviceRepo.findById(3L)).thenReturn(Optional.of(device));
        when(plantRepo.getPlants()).thenReturn(Arrays.asList(new Plant("PL001","Moranville")));
        when(devicePlantRepo.findByDeviceDeviceId(3L)).thenReturn(Optional.of(devicePlant));
        Exception ex = assertThrows(AssignmentConflictException.class, () -> service.assignDeviceToPlant(3L, "PL001"));
        assertEquals("Device 3 is already assigned to a plant (PL001), cannot reassign", ex.getMessage());
    }

    // assignGroupToDevice
    @Test
    void assignGroupToDevice_success() {
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.of(group));
        when(devicePlantRepo.findByDeviceDeviceId(anyLong())).thenReturn(Optional.of(devicePlant));
        when(groupPlantRepo.findByGroupId(anyLong())).thenReturn(Optional.of(groupPlant));
        when(groupDeviceRepo.findByGroupIdAndDeviceDeviceId(anyLong(), anyLong())).thenReturn(Optional.empty());
        service.assignGroupToDevice(3L, 2L);
        verify(groupDeviceRepo).save(any(GroupDevice.class));
    }
    @Test
    void assignGroupToDevice_deviceNotFound() {
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignGroupToDevice(3L, 2L));
        assertEquals("Device 2 not found", ex.getMessage());
    }
    @Test
    void assignGroupToDevice_groupNotFound() {
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> service.assignGroupToDevice(3L, 2L));
        assertEquals("Group 3 not found", ex.getMessage());
    }
    @Test
    void assignGroupToDevice_deviceNotAssignedToPlant() {
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.of(group));
        when(devicePlantRepo.findByDeviceDeviceId(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(AssignmentConflictException.class, () -> service.assignGroupToDevice(3L, 2L));
        assertEquals("Device 2 not assigned to any plant", ex.getMessage());
    }
    @Test
    void assignGroupToDevice_groupNotAssignedToPlant() {
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.of(group));
        when(devicePlantRepo.findByDeviceDeviceId(anyLong())).thenReturn(Optional.of(devicePlant));
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        when(groupPlantRepo.findByGroupId(anyLong())).thenReturn(Optional.empty());
        when(groupRepo.findById(anyLong())).thenReturn(Optional.of(group));
        when(devicePlantRepo.findByDeviceDeviceId(anyLong())).thenReturn(Optional.of(devicePlant));
        Exception ex = assertThrows(AssignmentConflictException.class, () -> service.assignGroupToDevice(3L, 2L));
        assertEquals("Group 3 not assigned to any plant", ex.getMessage());
    }
    @Test
    void assignGroupToDevice_differentPlants() {
        DevicePlant devicePlant2 = new DevicePlant();
        devicePlant2.setDevice(device);
        devicePlant2.setPlantKey("PLANT2");
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        GroupPlant groupPlant1 = new GroupPlant();
        groupPlant1.setPlantKey("3");
        DevicePlant devicePlant1 = new DevicePlant();
        devicePlant1 .setPlantKey("2");
        when(groupPlantRepo.findByGroupId(anyLong())).thenReturn(Optional.of(groupPlant1));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.of(group));
        when(devicePlantRepo.findByDeviceDeviceId(anyLong())).thenReturn(Optional.of(devicePlant1));
        Exception ex = assertThrows(AssignmentConflictException.class, () -> service.assignGroupToDevice(3L, 2L));
        assertEquals("Device 2 and Group 3 do not belong to the same plant", ex.getMessage());
    }
    @Test
    void assignGroupToDevice_conflict() {
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.of(group));
        when(deviceRepo.findById(anyLong())).thenReturn(Optional.of(device));
        when(devicePlantRepo.findByDeviceDeviceId(anyLong())).thenReturn(Optional.of(devicePlant));
        when(groupRepo.findById(anyLong())).thenReturn(Optional.of(group));
        when(groupPlantRepo.findByGroupId(anyLong())).thenReturn(Optional.of(groupPlant));
        when(groupDeviceRepo.findByGroupIdAndDeviceDeviceId(anyLong(), anyLong())).thenReturn(Optional.of(groupDevice));
        Exception ex = assertThrows(AssignmentConflictException.class, () -> service.assignGroupToDevice(3L, 2L));
        assertEquals("Device 2 is already assigned to group 3", ex.getMessage());
    }
} 