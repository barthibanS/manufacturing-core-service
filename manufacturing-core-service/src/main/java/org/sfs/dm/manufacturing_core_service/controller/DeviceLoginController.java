package org.sfs.dm.manufacturing_core_service.controller;

import org.sfs.dm.manufacturing_core_service.entity.LoginSession;
import org.sfs.dm.manufacturing_core_service.service.DeviceLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceLoginController {

    @Autowired
    private DeviceLoginService deviceLoginService;

    @PostMapping("/devices/{deviceId}/login")
    public ResponseEntity<String> login(@PathVariable Long deviceId, @RequestParam Long personId) {
        deviceLoginService.login(personId, deviceId);
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/devices/{deviceId}/logout")
    public ResponseEntity<String> logout(@PathVariable Long deviceId, @RequestParam Long personId) {
        deviceLoginService.logout(personId, deviceId);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/employees/{personId}/timelogs")
    public ResponseEntity<List<LoginSession>> getTimeLogs(@PathVariable Long personId) {
        List<LoginSession> logs = deviceLoginService.getTimeLogs(personId);
        return ResponseEntity.ok(logs);
    }
}

//SELECT * FROM PERSON_GROUP p join GROUP_ENTITY g on p.group_id = g.id  join GROUP_DEVICE gd on gd.group_id = g.id  join Device d  on d.device_id = gd.device_id