package org.sfs.dm.manufacturing_core_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sfs.dm.manufacturing_core_service.entity.Device;
import org.sfs.dm.manufacturing_core_service.model.DeviceEvent;
import org.sfs.dm.manufacturing_core_service.model.DeviceModel;
import org.sfs.dm.manufacturing_core_service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    private final WebClient webClient = WebClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<Device> getAllDevices() {
        log.info("Fetching all devices");
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceModel> getDeviceById(@PathVariable Long id) {
        log.info("Fetching device by id: {}", id);
        return deviceService.getDeviceById(id);
    }

    @PostMapping
    public DeviceModel createDevice(@RequestBody DeviceModel device) {
        log.info("Creating device: {}", device);
        return deviceService.createDevice(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceModel> updateDevice(@PathVariable Long id, @RequestBody DeviceModel device) {
        log.info("Updating device with id: {}", id);
        return deviceService.updateDevice(id, device);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.info("Deleting device with id: {}", id);
        return deviceService.deleteDevice(id);
    }

    @GetMapping(value = "/{deviceId}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<DeviceEvent>> streamDeviceEvents(@PathVariable Long deviceId) {
        String url = "https://mock-api.assessment.sfsdm.org/events/" + deviceId;
        log.info("Subscribing to SSE for deviceId: {} at {}", deviceId, url);
        return webClient.get()
                .uri(url)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .map(json -> {
                    DeviceEvent event = parseDeviceEvent(json);
                    log.info("Received event for deviceId {}: {}", deviceId, event);
                    return ServerSentEvent.builder(event).build();
                })
                .doOnError(e -> log.error("Error streaming SSE for deviceId {}: {}", deviceId, e.getMessage()));
    }

    private DeviceEvent parseDeviceEvent(String json) {
        try {
            return objectMapper.readValue(json, DeviceEvent.class);
        } catch (Exception e) {
            log.error("Failed to parse device event JSON: {}", json, e);
            return null;
        }
    }

}
