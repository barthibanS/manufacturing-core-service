package org.sfs.dm.manufacturing_core_service.controller;

import org.sfs.dm.manufacturing_core_service.service.AssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignments")
public class AssignmentsController {

    @Autowired
    private AssignmentsService assignmentsService;

    // 1. Assign Employee to Group
    @PostMapping("/employee-to-group")
    public ResponseEntity<?> assignEmployeeToGroup(@RequestParam Long personId, @RequestParam Long groupId) {
        assignmentsService.assignPersonToGroup(personId, groupId);
        return ResponseEntity.ok("Employee assigned to group");
    }

    // 2. Assign Group to Plant
    @PostMapping("/group-to-plant")
    public ResponseEntity<?> assignGroupToPlant(@RequestParam Long groupId, @RequestParam String plantId) {
        assignmentsService.assignGroupToPlant(groupId, plantId);
        return ResponseEntity.ok("Group assigned to plant");
    }

    // 3. Assign Device to Plant
    @PostMapping("/device-to-plant")
    public ResponseEntity<?> assignDeviceToPlant(@RequestParam Long deviceId, @RequestParam String plantId) {
        assignmentsService.assignDeviceToPlant(deviceId, plantId);
        return ResponseEntity.ok("Device assigned to plant");
    }

    // 4. Assign Group to Device
    @PostMapping("/group-to-device")
    public ResponseEntity<?> assignGroupToDevice(@RequestParam("groupId") Long groupId, @RequestParam("deviceId") Long deviceId) {
        assignmentsService.assignGroupToDevice(groupId, deviceId);
        return ResponseEntity.ok("Group assigned to device");
    }
}