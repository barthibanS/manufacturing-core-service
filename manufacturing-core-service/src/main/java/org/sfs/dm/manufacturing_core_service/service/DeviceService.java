package org.sfs.dm.manufacturing_core_service.service;

import org.sfs.dm.manufacturing_core_service.entity.Device;
import org.sfs.dm.manufacturing_core_service.model.DeviceModel;
import org.sfs.dm.manufacturing_core_service.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public ResponseEntity<DeviceModel> getDeviceById(Long id) {
        Optional<Device> device = deviceRepository.findById(id);
        return device
                .map(d -> ResponseEntity.ok(mapToDeviceModel(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public DeviceModel createDevice(DeviceModel deviceModel) {
        Device device = mapToDeviceEntity(deviceModel);
        Device savedDevice = deviceRepository.save(device);
        return mapToDeviceModel(savedDevice);
    }


    public ResponseEntity<DeviceModel> updateDevice(Long id, DeviceModel deviceDetails) {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            Device updatedDevice = device.get();
            updatedDevice.setType(deviceDetails.getType());
            updatedDevice.setRequiredOperators(deviceDetails.getRequiredOperators());
            Device savedDevice = deviceRepository.save(updatedDevice);
            DeviceModel model = mapToDeviceModel(savedDevice);
            return ResponseEntity.ok(model);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Void> deleteDevice(Long id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Device mapToDeviceEntity(DeviceModel model) {
        Device device = new Device();
        device.setDeviceId(model.getDeviceId());
        device.setType(model.getType());
        device.setRequiredOperators(model.getRequiredOperators());
        return device;
    }

    private DeviceModel mapToDeviceModel(Device device) {
        DeviceModel model = new DeviceModel();
        model.setDeviceId(device.getDeviceId());
        model.setType(device.getType());
        model.setRequiredOperators(device.getRequiredOperators());
        return model;
    }
}
